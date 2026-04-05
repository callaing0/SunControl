package com.suncontrol.common.service;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.WeatherContext;
import com.suncontrol.common.util.GenerateUtil;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.InverterGenerationDto;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.GenerationResultDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import com.suncontrol.core.dto.log.component.GenerateValueDto;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.log.RadiationLogService;
import com.suncontrol.core.service.log.WeatherLogService;
import com.suncontrol.core.util.TimeTruncater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
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

    @Transactional
    public void generateEnergy(int termSeconds) {
    /// 재료준비 1 - 자산정보
        Map<District, List<Long>> plantMap = plantService.findMapByDistrict();
        Map<Long, List<InverterGenerationDto>> invertersMap =
                inverterService.findAllByPlant();
    /// 재료준비 2 - 시작시간 - 끝시간 지정
        /// 시작시간 : 인버터 별 가장 최신 발전시각 중에서 "가장 오래된 시각"
        Map<Long, LocalDateTime> recentGenerated =
                generationLogService.getLastGeneratedTimeByAllInverters();
        LocalDateTime start = recentGenerated.values().stream()
                .min(LocalDateTime::compareTo)
                .orElseGet(() -> invertersMap.values().stream()
                        .flatMap(List::stream)
                        .map(InverterGenerationDto::getCreatedAt)
                        .min(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.now()));
        start = TimeTruncater.truncateToNextTerm(start, termSeconds);
        /// 끝시간 : 시작시간 1달 후 또는 현재 중 적은 값
        LocalDateTime end = Stream.of(start.plusMonths(1), LocalDateTime.now())
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
        end = TimeTruncater.truncateToTerm(end, termSeconds);
    /// 재료준비 3 - 기준시각을 토대로 한 날씨정보
        Map<District, Map<LocalDateTime, WeatherLogDto>> weatherLogMap =
                weatherLogService.findAllByDistrictAndTime(start, end);
        Map<District, Map<LocalDate, DailyWeatherDto>> dailyWeatherMap =
                dailyWeatherService.findAllByDistrictAndDate
                        (start.toLocalDate(), end.toLocalDate());
        Map<Long, Map<LocalDateTime, RadiationLogDto>> radiationLogMap =
                radiationLogService.findAllByPlantAndTime(start, end);
        /// 결과용 맵
        Map<Long, List<GenerationResultDto>> results = new HashMap<>();

        for(District district : District.LIST) {
            /// 1차 : 지역 key로 가공재료 꺼내기
            /// 시간별 날씨, 일간 날씨, 발전소 리스트
            Map<LocalDateTime, WeatherLogDto> weatherLogInnerMap =
                    weatherLogMap.get(district) == null ?
                            Collections.emptyMap() : weatherLogMap.get(district);
            Map<LocalDate, DailyWeatherDto> dailyWeatherInnerMap =
                    dailyWeatherMap.get(district) == null ?
                            Collections.emptyMap() : dailyWeatherMap.get(district);
            List<Long> plants = plantMap.get(district) == null ?
                    Collections.emptyList() : plantMap.get(district);

            for(Long plantId : plants) {
                /// 2차 : 발전소 ID key 로 가공재료 꺼내기
                /// 일사량 데이터, 인버터 리스트
                Map<LocalDateTime, RadiationLogDto> radiationLogInnerMap =
                        radiationLogMap.get(plantId) == null ?
                                Collections.emptyMap() : radiationLogMap.get(plantId);
                List<InverterGenerationDto> inverters =
                        invertersMap.get(plantId) == null ?
                                Collections.emptyList() : invertersMap.get(plantId);
                for(InverterGenerationDto inverter : inverters) {
                    /// 인버터 별 생성 시작시간 정하기
                    LocalDateTime inverterStart =
                            recentGenerated.get(inverter.getId()) == null ?
                            start : recentGenerated.get(inverter.getId());
                    /// 세부 로직 호출
                /// TODO : getResult 파라미터 추가 (날씨정보 묶음 & 인버터 스펙)
                    List<GenerationResultDto> result =
                            getResult(
                                    inverterStart, end, inverter,
                                    new WeatherContext(
                                            weatherLogInnerMap,
                                            radiationLogInnerMap,
                                            dailyWeatherInnerMap),
                                    termSeconds);
                    results.put(inverter.getId(), result);
                }
            }
        }
        /// 전체 결과 DB 저장
        generationLogService.saveAll(results);
    }

    public Map<Long, List<GenerationResultDto>> getPredict
            (LocalDateTime start, LocalDateTime end) {
        return Collections.emptyMap();
    }

    private List<GenerationResultDto> getResult
            (LocalDateTime start, LocalDateTime end, InverterGenerationDto inv,
             WeatherContext context, int termSecond) {
        List<GenerationResultDto> resultList = new ArrayList<>();
        LocalDateTime current = start;

        BigDecimal lastAccumEnergy = inv.getLastAccumEnergy();
        while(current.isBefore(end)) {
            /// 날씨조회 기준시각 설정, 날씨정보 가져오기
            LocalDateTime baseTime =
                    TimeTruncater.truncateToTerm(current, 3600);
            GenerateCalcBase base = context.getGenerateCalcBase(baseTime);

            /// 사용할 생성 엔진 정하기
            GenerateUtil expStrategy = utilMap.get(base.expStrategy().getLabel());
            GenerateUtil actStrategy = utilMap.get(
                    inv.getInverterType().getStrategy().getLabel());

            /// 전문 계산로직으로 연산 처리
            GenerateValueDto dto = new GenerateValueDto();
            dto = expStrategy.generateEnergy(current, inv, base, dto);
            dto = actStrategy.generateEnergy(current, inv, base, dto);

            lastAccumEnergy = calculateAccumEnergy(
                    lastAccumEnergy, dto.getValueActual(), termSecond);

            resultList.add(new GenerationResultDto(
                    inv.getId(), current, dto, lastAccumEnergy ,base.weather()));

            current = TimeTruncater.truncateToNextTerm(current, termSecond);
        }
        return resultList;
    }

    private static final BigDecimal SECONDS_PER_HOUR = BigDecimal.valueOf(3600);

    private BigDecimal calculateAccumEnergy(
            BigDecimal currentEnergy, BigDecimal valueActual, int termSecond) {
        /// 누적발전량 집계
        /// 현재 출력량과 termSecond를 곱하여 값 측정
        return currentEnergy.add(
                valueActual.multiply(
                        BigDecimal.valueOf(termSecond)
                                .divide(SECONDS_PER_HOUR,10,
                                        RoundingMode.HALF_UP)));
    }
}
