package com.suncontrol.common.service;

import com.suncontrol.common.dto.report.ReportCalcDto;
import com.suncontrol.common.dto.report.ReportStoppedCalcDto;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.asset.InverterDto;
import com.suncontrol.core.dto.asset.PlantDto;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.dto.component.InverterBaseDto;
import com.suncontrol.core.dto.component.InverterMeta;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.report.*;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.report.*;
import com.suncontrol.core.util.DataCollectorsUtil;
import com.suncontrol.core.util.TimeTruncater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class ActualGenerationReportService extends AbstractGenerationReportService{

    public ActualGenerationReportService(GenerationLogService generationLogService, HourlyReportService hourlyReportService, DailyReportService dailyReportService, MonthlyReportService monthlyReportService, PlantService plantService, InverterService inverterService, DailyWeatherService dailyWeatherService, GenerationEnergyService generationEnergyService) {
        super(generationLogService, hourlyReportService, dailyReportService, monthlyReportService, plantService, inverterService, dailyWeatherService, generationEnergyService);
    }

    @Override
    protected Map<LocalDateTime, Map<Long, List<GenerationLogDto>>> getRawSource(LocalDateTime start, LocalDateTime end) {
        return DataCollectorsUtil.groupToNestedListMap(
                /// findAllByBaseTimeBetween(start, end)
                getGenerationLogService()
                        .findAllByTimeBetween(start, end),
                (GenerationLogDto log) ->
                        log.truncateBaseTime(StaticValues.HOUR_SECONDS),
                GenerationLogDto::getInverterId
        );
    }

    @Override
    protected Map<LocalDate, Map<Long, List<HourlyReportDto>>> getHourlySource(LocalDateTime start, LocalDateTime end, int dayOffset) {
        return DataCollectorsUtil.groupToNestedListMap(
                getHourlyReportService()
                        .findAllByBaseTimeBetweenStartAndEnd
                                (start, end, dayOffset),
                HourlyReportDto::truncateBaseDate,
                HourlyReportDto::getInverterId
        );
    }

    @Override
    protected Map<String, Map<Long, List<DailyReportDto>>> getDailySource(LocalDate start, LocalDate end) {
        return DataCollectorsUtil.groupToNestedListMap(
                getDailyReportService().findAllByDateBetween(start, end, ReportDataType.ACTUAL_SNAPSHOT.getDayOffset()),
                DailyReportDto::getBaseMonth,
                DailyReportDto::getInverterId
        );
    }

    @Override
    protected List<HourlyReportDto> hourlyReport(
            LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        log.info("{}에서 {}까지의 {} 통계생성", start, end, reportDataType.getReportDescription());
        Map<Long, Map<LocalDateTime, List<GenerationLogDto>>> generationInvMap =
                DataCollectorsUtil.transpose(getRawSource(start, end));
        Map<Long, Map<LocalDateTime, HourlyReportDto>> previousMap
                = DataCollectorsUtil.groupToMap(
                        getHourlyReportService().findAllByBaseTimeBetweenStartAndEnd(
                                start.minusDays(1), end.minusDays(1), reportDataType.getDayOffset()
                        ),
                        HourlyReportDto::getInverterId,
                        HourlyReportDto::getBaseTime
        );
        /// 루프를 위한 인버터 리스트
        List<InverterMeta> inverterList = getInverterList();

        List<HourlyReportDto> resultList = new ArrayList<>();

        for(InverterMeta inverter : inverterList) {
            Long inverterId = inverter.getInverterId();
            LocalDateTime currentTime =
                    TimeTruncater.truncateToNextTerm(start, StaticValues.HOUR_SECONDS);
            Map<LocalDateTime, HourlyReportDto> prevInnerMap =
                    previousMap.getOrDefault
                            (inverterId, new HashMap<>());
            Map<LocalDateTime, List<GenerationLogDto>> genLogInnverMap =
                    generationInvMap.getOrDefault
                            (inverterId, Collections.emptyMap());
            // 상세로직
            while(!currentTime.isAfter(end)) {
                HourlyReportDto oneDayPrevious = prevInnerMap.get(currentTime.minusDays(1));
                List<GenerationLogDto> genList = genLogInnverMap.get(currentTime);
                if(genList == null || genList.isEmpty()) {
                    log.debug("{} inverter 의 {} 기록없음", inverterId, currentTime);
                    currentTime = TimeTruncater.truncateToNextTerm
                            (currentTime, StaticValues.HOUR_SECONDS);
                    continue;
                }
                /// 지금의 기록이 "이 인버터의 최초 기록" 인지 판별하는 변수
                boolean isFirst = TimeTruncater
                        .toReportCeiling(
                                inverter.getCreatedAt(),
                                StaticValues.HOUR_SECONDS)
                        .isEqual(currentTime);
                LocalDateTime prevTime = isFirst ? inverter.getCreatedAt() : currentTime.minusHours(1);

                GenerationValuesDto resultSet =
                        new ReportCalcDto(
                                currentTime,
                                (oneDayPrevious != null) ? oneDayPrevious.getValueActual() : null,
                                DataCollectorsUtil.toDataList(
                                        genList,
                                        GenerationLogDto::getValuesDto
                                        ),
                                StaticValues.HOUR_SECONDS,
                                prevTime
                                )
                                .getValues();
                HourlyReportDto result = new HourlyReportDto(
                        resultSet,
                        inverter.getCapacity(),
                        genList.get(genList.size() - 1).getWeatherCode(),
                        reportDataType
                );
                resultList.add(result);
                prevInnerMap.put(currentTime, result);
                prevInnerMap.remove(currentTime.minusDays(3));
                currentTime = TimeTruncater.truncateToNextTerm
                        (currentTime, StaticValues.HOUR_SECONDS);
            }

        }
        return resultList;
    }

    @Override
    protected List<DailyReportDto> dailyReport
            (LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        log.info("{} 부터 {} 까지의 {} 일일통계생성", start, end, reportDataType.getReportDescription());
        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        /// 통계작성용 원천데이터
        Map<Long, Map<LocalDate, List<HourlyReportDto>>> hourlyReportMap =
                DataCollectorsUtil.transpose(getHourlySource(start, end, reportDataType.getDayOffset()));
        /// 비교용 전일 데이터
        Map<Long, DailyReportDto> previousMap = DataCollectorsUtil.mapBy(
                getDailyReportService().findAllLatestByInverter(reportDataType.getDayOffset()),
                DailyReportDto::getInverterId
        );
        /// 루프를 위한 인버터 리스트
        List<InverterMeta> inverterList = getInverterList();
        /// 날씨조회를 위한 발전소ID 지역맵
        Map<Long, District> plantDistrictMap =
                DataCollectorsUtil.mapBy(
                        getPlantService().findAllActive(),
                        PlantDto::getId,
                        PlantDto::getDistrict
                );
        /// 날씨조회용 맵
        Map<LocalDate, Map<District, DailyWeatherDto>> weatherMap =
                DataCollectorsUtil.groupToMap(
                        getDailyWeatherService().findLatest(startDate, endDate),
                        DailyWeatherDto::getBaseDate,
                        DailyWeatherDto::getDistrict
                );

        List<DailyReportDto> resultList = new ArrayList<>();

        for(InverterMeta inverter : inverterList) {
            Long inverterId = inverter.getInverterId();
            Map<LocalDate, List<HourlyReportDto>> sourceInnerMap = hourlyReportMap.getOrDefault(inverterId, Collections.emptyMap());
            DailyReportDto previous = previousMap.getOrDefault(inverterId, null);

            LocalDate current = startDate;

            while(current.isBefore(endDate)) {
                Map<District, DailyWeatherDto> dailyWeatherInnerMap = weatherMap.getOrDefault(current, Collections.emptyMap());
                List<HourlyReportDto> sources = sourceInnerMap.getOrDefault(current, Collections.emptyList());

                if(sources == null || sources.isEmpty()) {
                    log.debug("{} inverter 의 {} 기록없음", inverter.getInverterId(), current);
                    current = current.plusDays(1);
                    continue;
                }

                ReportStoppedCalcDto resultSet =
                        new ReportStoppedCalcDto(
                                current.atStartOfDay(),
                                (previous != null) ?
                                        previous.getValueActual() : null,
                                DataCollectorsUtil.toDataList(
                                        sources,
                                        HourlyReportDto::getValuesDto
                                ),
                                StaticValues.DAY_SECONDS,
                                (previous != null) ?
                                        previous.getBaseDate().atStartOfDay() :
                                        inverter.getCreatedAt()
                        );

                Integer weatherCode = Optional.ofNullable(inverter.getPlantId())
                        .map(plantDistrictMap::get)
                        .map(dailyWeatherInnerMap::get)
                        .map(DailyWeatherDto::getWeatherCode)
                        .orElse(Weather.CLEAR_SKY.getWmo());

                DailyReportDto result = new DailyReportDto(
                        resultSet.getValues(),
                        inverter.getCapacity(),
                        weatherCode,
                        reportDataType,
                        resultSet.getStoppedDto()
                );
                resultList.add(result);
                previous = result;

                current = current.plusDays(1);
            }
        }

        return resultList;
    }

    @Override
    protected List<MonthlyReportDto> monthlyReport(LocalDate start, LocalDate end) {

        /// 통계 생성용 원천 데이터 불러오기
        Map<Long, Map<String, List<DailyReportDto>>> dailyReportMap = DataCollectorsUtil.groupToNestedListMap(
                getDailyReportService().findAllByDateBetween(start, end, ReportDataType.ACTUAL_SNAPSHOT.getDayOffset()),
                DailyReportDto::getInverterId,
                DailyReportDto::getBaseMonth
        );
        /// 비교용 전월 데이터
        Map<Long, Map<String, MonthlyReportDto>> previousMap = DataCollectorsUtil.groupToMap(
                getMonthlyReportService().findAllByMonthBetween(start.minusMonths(1), end.minusMonths(1)),
                MonthlyReportDto::getInverterId,
                MonthlyReportDto::getBaseMonth
        );
        /// 루프를 위한 인버터 리스트
        List<InverterMeta> inverterList = getInverterList();
        /// 결과값 저장하는 리스트
        List<MonthlyReportDto> resultList = new ArrayList<>();

        for(InverterMeta inverter : inverterList) {
            Long inverterId = inverter.getInverterId();
            Map<String, List<DailyReportDto>> dailyInnerMap = dailyReportMap.getOrDefault(inverterId, Collections.emptyMap());
            Map<String, MonthlyReportDto> prevInnerMap = previousMap.getOrDefault(inverterId, Collections.emptyMap());

            LocalDate current = start;
            while(current.isBefore(end)) {
                String currentMonth = TimeTruncater.getBaseMonth(current);
                String prevMonth = TimeTruncater.getBaseMonth(current.minusMonths(1));

                List<DailyReportDto> dailyList = dailyInnerMap.get(currentMonth);
                MonthlyReportDto currentReport = prevInnerMap.get(currentMonth);

                if(dailyList == null || dailyList.isEmpty() || currentReport != null) {
                    continue;
                }
                MonthlyReportDto previous = prevInnerMap.get(prevMonth);
                currentReport = new MonthlyReportDto();

                ReportStoppedCalcDto resultSet = new ReportStoppedCalcDto(
                        current.atStartOfDay(),
                        previous != null ? previous.getValueActual() : null,
                        DataCollectorsUtil.toDataList(
                                dailyList,
                                DailyReportDto::getValuesDto
                        ),
                        StaticValues.DAY_SECONDS * current.lengthOfMonth(),
                        previous != null ? null : inverter.getCreatedAt()
                );

                resultList.add(new MonthlyReportDto(resultSet.getValues(), DataCollectorsUtil.toDataList(dailyList, DailyReportDto::getStoppedDto), inverter.getCapacity()));

                current = current.plusMonths(1);
            }
        }

        return resultList;
    }

    @Override
    protected LocalDate getStartDateForMonthly(LocalDate defaultStartDate) {
        /// defaultStartDate 를 항상 매 월 1일로 받도록.
        List<MonthlyReportDto> dtoList = getMonthlyReportService().findAllLatestByInverter();

        if(dtoList.isEmpty()) {
            return getFirstInverterTime(defaultStartDate.atStartOfDay()).toLocalDate();
        }

        return TimeTruncater.getOldestDateOrDefault(
                dtoList,
                defaultStartDate,
                MonthlyReportDto::getBaseDate
        );
    }

    @Override
    protected LocalDate getEndDateForMonthly(LocalDate defaultEndDate, LocalDate startDate) {
        return TimeTruncater.getOlderDate(startDate.plusMonths(1), defaultEndDate);
    }

    @Override
    protected LocalDateTime getEndTimeForDaily(LocalDateTime startTime, LocalDateTime defaultStartTime, ReportDataType reportDataType) {
        boolean isAfterSunset = !defaultStartTime.toLocalTime()
                .isBefore(LocalTime.of(8,0));
        LocalDateTime nowPlusDayOffset =
                defaultStartTime.plusDays(
                        reportDataType.getDayOffset() + (isAfterSunset ? 1 : 0)
                ).toLocalDate().atStartOfDay();
        return TimeTruncater.getOldestTimeOrDefault(
                List.of(startTime.plusMonths(1), nowPlusDayOffset),
                nowPlusDayOffset
        );
    }

    @Override
    protected LocalDateTime getStartTimeForDaily(LocalDateTime defaultStartTime, ReportDataType reportDataType) {
        List<DailyReportDto> dtoList = getDailyReportService().findAllLatestByInverter(reportDataType.getDayOffset());
        if(dtoList.isEmpty()) {
            return getFirstInverterTime(defaultStartTime);
        }
        return TimeTruncater.getOldestTimeOrDefault(
                dtoList,
                defaultStartTime,
                dto -> dto.getBaseDate().plusDays(1).atStartOfDay()
        );
    }

    @Override
    protected LocalDateTime getStartTimeForHourly
            (LocalDateTime defaultTime,ReportDataType reportDataType) {

        /// 가장 오래된 통계의 최신기록 구하기
        List<HourlyReportDto> dtoList =
                getHourlyReportService().findAllLatestByInverter
                        (reportDataType.getDayOffset());

        /// 가장 오래된 인버터의 등록시간 구하기
        if(dtoList.isEmpty()) {
            return getFirstInverterTime(defaultTime);
        }
        return TimeTruncater.getOldestTimeOrDefault(
                dtoList,
                defaultTime,
                dto -> dto.getBaseTime().plusSeconds(StaticValues.HOUR_SECONDS)
        );
    }

    @Override
    protected LocalDateTime getEndTimeForHourly(LocalDateTime start, ReportDataType reportDataType) {
        LocalDateTime nowPlusDayOffset =
                TimeTruncater.truncateToTerm(
                        LocalDateTime.now().plusDays(
                                reportDataType.getDayOffset()), StaticValues.HOUR_SECONDS);
        return TimeTruncater.getOldestTimeOrDefault(
                List.of(start.plusMonths(1), nowPlusDayOffset),
                nowPlusDayOffset
        );
    }

    private LocalDateTime getFirstInverterTime(LocalDateTime defaultTime) {
        return TimeTruncater.getOldestTimeOrDefault(
                getInverterService().findAllActive(),
                defaultTime,
                InverterDto::getCreatedAt
        );
    }
}
