package com.suncontrol.common.service;

import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.dto.report.HourlyReportDto;
import com.suncontrol.core.dto.report.MonthlyReportDto;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.report.DailyReportService;
import com.suncontrol.core.service.report.HourlyReportService;
import com.suncontrol.core.service.report.MonthlyReportService;
import com.suncontrol.core.util.DataCollectorsUtil;
import com.suncontrol.core.util.TimeTruncater;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ActualGenerationReportService extends AbstractGenerationReportService{

    public ActualGenerationReportService(GenerationLogService generationLogService, HourlyReportService hourlyReportService, DailyReportService dailyReportService, MonthlyReportService monthlyReportService, PlantService plantService, DailyWeatherService dailyWeatherService, GenerationEnergyService generationEnergyService) {
        super(generationLogService, hourlyReportService, dailyReportService, monthlyReportService, plantService, dailyWeatherService, generationEnergyService);
    }

    @Override
    protected Map<LocalDateTime, Map<Long, List<GenerationLogDto>>> getRawSource(LocalDateTime start, LocalDateTime end) {
        return DataCollectorsUtil.groupToNestedListMap(
                /// TODO 상태와 상관없이 모든 데이터 가져올 것
                /// findAllByBaseTimeBetween(start, end)
                getGenerationLogService()
                        .findAllbyStatus(start, end, GenerationStatus.PENDING, false),
                (GenerationLogDto log) ->
                        log.truncateBaseTime(StaticValues.HOUR_SECONDS),
                GenerationLogDto::getInverterId
        );
    }

    @Override
    protected Map<Long, List<HourlyReportDto>> getHourlySource(LocalDateTime start, LocalDateTime end) {
        return Map.of();
    }

    @Override
    protected Map<Long, List<DailyReportDto>> getDailySource(LocalDate start, LocalDate end) {
        return Map.of();
    }

    @Override
    protected List<HourlyReportDto> hourlyReport(
            LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        Map<LocalDateTime, Map<Long, List<GenerationLogDto>>> generationInvMap =
                getRawSource(start, end);
        Map<LocalDateTime, Map<Long, HourlyReportDto>> previousMap
                = DataCollectorsUtil.groupToMap(
                        getHourlyReportService().findAllByBaseTimeBetweenStartAndEnd(
                                start.minusDays(1), end.minusDays(1)
                        ),
                        HourlyReportDto::getBaseTime,
                        HourlyReportDto::getInverterId
        );
        LocalDateTime currentTime = TimeTruncater.truncateToTerm(start, StaticValues.HOUR_SECONDS);
        // 통계 생성은 인버터별로 '별도처리'를 할 필요가 없다.
        while(currentTime.isBefore(
                TimeTruncater.truncateToNextTerm(end, StaticValues.HOUR_SECONDS))) {
            Map<Long, HourlyReportDto> prevInnerMap =
                    previousMap.getOrDefault
                            (currentTime.minusDays(1), Collections.emptyMap());
            Map<Long, List<GenerationLogDto>> genLogInnverMap =
                    generationInvMap.getOrDefault
                            (currentTime, Collections.emptyMap());
            // 상세로직
            for(Long inverterId : genLogInnverMap.keySet()) {
                HourlyReportDto previous = prevInnerMap.get(inverterId);
                List<GenerationLogDto> genList = genLogInnverMap.get(inverterId);

                /// TODO 시간당 데이터 계산로직
            }

            currentTime = currentTime.plusHours(1);
        }
        return List.of();
    }

    @Override
    protected List<DailyReportDto> dailyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        return List.of();
    }

    @Override
    protected List<MonthlyReportDto> monthlyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType) {
        return List.of();
    }
}
