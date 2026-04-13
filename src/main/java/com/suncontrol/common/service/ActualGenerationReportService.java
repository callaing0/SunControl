package com.suncontrol.common.service;

import com.suncontrol.common.dto.report.ReportCalcDto;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.asset.InverterDto;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.dto.component.InverterBaseDto;
import com.suncontrol.core.dto.component.InverterMeta;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    protected Map<Long, List<DailyReportDto>> getDailySource(LocalDate start, LocalDate end) {
        return Map.of();
    }

    @Override
    protected List<HourlyReportDto> hourlyReport(
            LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        log.info("{}에서 {}까지의 {} 통계생성", start, end, reportDataType.getReportDescription());
        Map<LocalDateTime, Map<Long, List<GenerationLogDto>>> generationInvMap =
                getRawSource(start, end);
        Map<LocalDateTime, Map<Long, HourlyReportDto>> previousMap
                = DataCollectorsUtil.groupToMap(
                        getHourlyReportService().findAllByBaseTimeBetweenStartAndEnd(
                                start.minusDays(1), end.minusDays(1), reportDataType.getDayOffset()
                        ),
                        HourlyReportDto::getBaseTime,
                        HourlyReportDto::getInverterId
        );
        Map<Long, InverterMeta> inverterMap =
                DataCollectorsUtil.mapBy(
                        getInverterService().findAllActive(),
                        InverterBaseDto::getInverterId,
                        InverterMeta::new
                );

        List<HourlyReportDto> resultList = new ArrayList<>();

        LocalDateTime currentTime =
                TimeTruncater.truncateToNextTerm(start, StaticValues.HOUR_SECONDS);
        // 통계 생성은 인버터별로 '별도처리'를 할 필요가 없다.
        while(!currentTime.isAfter(end)) {
            Map<Long, HourlyReportDto> prevInnerMap =
                    previousMap.getOrDefault
                            (currentTime.minusDays(1), Collections.emptyMap());
            Map<Long, List<GenerationLogDto>> genLogInnverMap =
                    generationInvMap.getOrDefault
                            (currentTime, Collections.emptyMap());
            // 상세로직
            for(Long inverterId : inverterMap.keySet()) {
                HourlyReportDto previous = prevInnerMap.get(inverterId);
                List<GenerationLogDto> genList = genLogInnverMap.get(inverterId);
                if(genList == null || genList.isEmpty()) {
                    log.warn("{} inverter 의 {} 기록없음", inverterId, currentTime);
                    continue;
                }
                /// 지금의 기록이 "이 인버터의 최초 기록" 인지 판별하는 변수
                boolean isFirst = TimeTruncater
                        .toReportCeiling(
                                inverterMap.get(inverterId).getCreatedAt(),
                                StaticValues.HOUR_SECONDS)
                        .isEqual(currentTime);

                GenerationValuesDto result =
                        new ReportCalcDto(
                                currentTime,
                                (previous != null) ? previous.getValuesDto() : null,
                                DataCollectorsUtil.toDataList(
                                        genList,
                                        GenerationLogDto::getValuesDto
                                        ),
                                StaticValues.HOUR_SECONDS,
                                (isFirst) ? null : inverterMap.get(inverterId).getCreatedAt()
                                )
                                .getValues();
                resultList.add(
                        new HourlyReportDto(
                                result,
                                inverterMap.get(inverterId).getCapacity(),
                                genList.get(genList.size() - 1).getWeatherCode(),
                                reportDataType
                        )
                );
            }

            currentTime = TimeTruncater.truncateToNextTerm
                    (currentTime, StaticValues.HOUR_SECONDS);
        }
        return resultList;
    }

    @Override
    protected List<DailyReportDto> dailyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        return List.of();
    }

    @Override
    protected List<MonthlyReportDto> monthlyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        return List.of();
    }

    @Override
    protected LocalDateTime getEndTimeForDaily(LocalDateTime startTime, LocalDateTime defaultStartTime, ReportDataType reportDataType) {
        return null;
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
                dto -> dto.getBaseDate().atStartOfDay()
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
