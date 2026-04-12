package com.suncontrol.common.service;

import com.suncontrol.common.dto.generate.*;
import com.suncontrol.common.util.GenerateUtil;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.asset.InverterDto;
import com.suncontrol.core.dto.asset.InverterUpdateDto;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.log.RadiationLogService;
import com.suncontrol.core.service.log.WeatherLogService;
import com.suncontrol.core.util.DataCollectorsUtil;
import com.suncontrol.core.util.TimeTruncater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerationEnergyService {
    ///  발전기록 raw data 생성 오케스트레이터

    private final PlantService plantService;
    private final InverterService inverterService;
    private final WeatherLogService weatherLogService;
    private final RadiationLogService radiationLogService;
    private final DailyWeatherService dailyWeatherService;
    private final GenerationLogService generationLogService;

    /**
     * 인버터 종류별 발전생성 유틸 EnumMap
     * */
    private final Map<String, GenerateUtil> utilMap;

    public void generateEnergy(int termSeconds) {
    // region 1. 재료준비 1 - 자산정보
        /// 살아있는 발전소 정보 가져오기
        Map<District, List<Long>> plantMap = plantService.getPlantMapByDistrict();
        /// 살아있는 인버터 정보 가져와서 발전용 정보만 가진 객체로 변환
        List<InverterDto> inverterList = inverterService.findAllActive();
        Map<Long, List<InverterGenerationDto>> invertersMap =
                DataCollectorsUtil.groupBy(
                        inverterList,
                        InverterDto::getPlantId,
                        InverterGenerationDto::new
                );
    // endregion

    // region 2. 재료준비 2 - 시작시간 - 끝시간 지정
        /// 시작시간 : 인버터 별 가장 최신 발전시각 중에서 "가장 오래된 시각"
        Map<Long, LocalDateTime> recentGenerated =
                generationLogService.getLastGeneratedTimeByAllInverters();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime defaultStart = TimeTruncater.getOldestTimeOrDefault
                (inverterList, now, InverterDto::getBaseTime);
        LocalDateTime start = TimeTruncater
                .getOldestTimeOrDefault(recentGenerated, defaultStart);
        /// 시작시간을 발전량 생성 기준으로 "평탄화" (당시 시각의 다음 시간대)
        start = TimeTruncater.truncateToNextTerm(start, termSeconds);
        /// 끝시간 : 시작시간 1달 후 또는 현재 중 적은 값
        LocalDateTime monthLater = start.plusMonths(1);
        LocalDateTime end = TimeTruncater.getOldestTimeOrDefault(
                List.of(monthLater, now), now);
        /// 끝시간을 발전량 생성 기준으로 "평탄화" (당시 시각의 이전 시간대)
        end = TimeTruncater.truncateToTerm(end, termSeconds);
    // endregion

    // region 3. 재료준비 3 - 기준시각을 토대로 한 날씨정보
        Map<District, Map<LocalDateTime, WeatherLogDto>> weatherLogMap =
                DataCollectorsUtil.groupToMap(
                        weatherLogService.findLatest(start, end),
                        WeatherLogDto::getDistrict,
                        WeatherLogDto::getBaseTime
                );

        Map<District, Map<LocalDate, DailyWeatherDto>> dailyWeatherMap =
                DataCollectorsUtil.groupToMap(
                        dailyWeatherService.findLatest(
                                start.toLocalDate(), end.toLocalDate()),
                        DailyWeatherDto::getDistrict,
                        DailyWeatherDto::getBaseDate
                );

        Map<Long, Map<LocalDateTime, RadiationLogDto>> radiationLogMap =
                DataCollectorsUtil.groupToMap(
                        radiationLogService.findLatest(start, end),
                        RadiationLogDto::getPlantId,
                        RadiationLogDto::getBaseTime
                );
    // endregion
        /// 결과용 맵
        List<GenerationLogDto> results = new ArrayList<>();
        List<InverterUpdateDto> inverterUpdateList = new ArrayList<>();

        log.info("collect data at  {}", LocalDateTime.now());
        log.info("collect data from {} to {}", start, end);
        for(District district : District.LIST) {
            // region 4. 1차 : 지역 key로 가공재료 꺼내기
            /// 시간별 날씨, 일간 날씨, 발전소 리스트
            Map<LocalDateTime, WeatherLogDto> weatherLogInnerMap =
                    weatherLogMap.getOrDefault(district, Collections.emptyMap());

            Map<LocalDate, DailyWeatherDto> dailyWeatherInnerMap =
                    dailyWeatherMap.getOrDefault(district, Collections.emptyMap());

            List<Long> plants =
                    plantMap.getOrDefault(district, Collections.emptyList());
            // endregion

            for(Long plantId : plants) {
                // region 5. 2차 : 발전소 ID key 로 가공재료 꺼내기
                /// 일사량 데이터, 인버터 리스트
                Map<LocalDateTime, RadiationLogDto> radiationLogInnerMap =
                        radiationLogMap.getOrDefault(plantId, Collections.emptyMap());
                List<InverterGenerationDto> inverters =
                        invertersMap.getOrDefault(plantId, Collections.emptyList());
                // endregion
                for(InverterGenerationDto inverter : inverters) {
                    /// 인버터 별 생성 시작시간 정하기
                    LocalDateTime inverterStart =
                            recentGenerated.getOrDefault(inverter.getId(), start);

                    // region 6. 세부 로직 호출
                /// TODO : getResult 파라미터 추가 (날씨정보 묶음 & 인버터 스펙)
                    GenerationResultSet result =
                            getResult(
                                    inverterStart, end, inverter,
                                    new WeatherContext(
                                            weatherLogInnerMap,
                                            radiationLogInnerMap,
                                            dailyWeatherInnerMap),
                                    termSeconds);
                // endregion
                    results.addAll(
                            DataCollectorsUtil.toDataList(
                                    result.getResults(),
                                    GenerationResultDto::getGenerationLogDto
                            )
                    );
                    inverterUpdateList.add(result.getInverter().getUpdateSet());


                }
            }
        }
        int count = results.size();
        log.info("calculated {} data at {}", count, LocalDateTime.now());
        /// 전체 결과를 LogDto 리스트로 뜯어서 DB 저장
        saveAll(results, inverterUpdateList);
    }

    @Transactional
    protected void saveAll(List<GenerationLogDto> resultLogs,
                           List<InverterUpdateDto> inverterUpdateList) {
        generationLogService.saveAll(resultLogs);
        inverterService.updateAccumAndStatus(inverterUpdateList);

    }

    public Map<Long, List<GenerationResultDto>> getPredict
            (LocalDateTime start, LocalDateTime end) {
        /// TODO
        return Collections.emptyMap();
    }

    private GenerationResultSet getResult
            (LocalDateTime start, LocalDateTime end, InverterGenerationDto inv,
             WeatherContext context, int termSecond) {
        log.info("{}인버터 {}부터 {}까지의 기록생성", inv.getId(), start, end);
        List<GenerationResultDto> resultList = new ArrayList<>();
        LocalDateTime current = start;

        BigDecimal lastAccumEnergy = inv.getLastAccumEnergy();
        while(!current.isAfter(end)) {
            /// 날씨조회 기준시각 설정, 날씨정보 가져오기
            LocalDateTime baseTime =
                    TimeTruncater.truncateToTerm(current, 3600);
            GenerateCalcBase base = context.getGenerateCalcBase(baseTime);

            /// 사용할 생성 엔진 정하기
            GenerateUtil expStrategy = utilMap.get
                    (base.expStrategy().getLabel());
            GenerateUtil actStrategy = utilMap.get
                    (inv.getInverterType().getStrategy().getLabel());

            /// 전문 계산로직으로 연산 처리
            GenerateDataContext gContext = new GenerateDataContext(
                    current, inv, base, new GenerateValueDto()
            );
            gContext = expStrategy.generateEnergy(gContext);
            gContext = actStrategy.generateEnergy(gContext);
            /// 계산이 끝난 값은 capacity 기준으로 클리핑
            gContext.getDto().setCapacity
                    (inv.getRatedCapacity(), inv.getMeasuredCapacity());
            GenerateValueDto dto = gContext.getDto();

            lastAccumEnergy = calculateAccumEnergy(
                    lastAccumEnergy, dto.getValueActual(), termSecond);
            /// inverter를 최신상태로 업데이트
            inv = gContext.getInv();
            inv.setCurrentPower(dto.getValueActual());

            GenerationResultDto result =
                    new GenerationResultDto(
                            inv.getId(),
                            current,
                            dto,
                            lastAccumEnergy,
                            base.weatherCode()
                    );
            /// 테스트 데이터 출력
            log.debug("Generation result: {}", result);

            resultList.add(result);

            current = TimeTruncater.truncateToNextTerm
                    (current.plusHours(1), termSecond);
        }
        /// 최종 누적발전량 업데이트
        inv.setLastAccumEnergy(lastAccumEnergy);
        return new GenerationResultSet(resultList, inv);
    }

    private BigDecimal calculateAccumEnergy(
            BigDecimal currentEnergy, BigDecimal valueActual, int termSecond) {
        /// 누적발전량 집계
        /// 현재 출력량과 termSecond를 곱하여 값 측정
        return currentEnergy.add(
                valueActual.multiply(
                        BigDecimal.valueOf(termSecond)
                                .divide(StaticValues.SECONDS_PER_HOUR,10,
                                        RoundingMode.HALF_UP)));
    }
}
