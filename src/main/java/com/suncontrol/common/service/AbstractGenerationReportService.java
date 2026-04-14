package com.suncontrol.common.service;

import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.report.*;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.report.*;
import com.suncontrol.core.util.TimeTruncater;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractGenerationReportService {
    private final GenerationLogService generationLogService;
    private final HourlyReportService hourlyReportService;
    private final DailyReportService dailyReportService;
    private final MonthlyReportService monthlyReportService;
    private final PlantService plantService;
    private final InverterService inverterService;
    private final DailyWeatherService dailyWeatherService;
    private final GenerationEnergyService generationEnergyService;

    /// 통계 작성하는 프로세스 메서드
    /// 파라미터 예시 ) ReportDataType.ACTUAL_SNAPSHOT 등
    /// TODO : 시간 파라미터를 받을 게 아니고 process가 돌릴 시간을 결정해야 할거같은데?
    public void process(ReportDataType reportDataType){
        /// 잘못된 파라미터 요청이 들어오면 뱉어버린다.
        if(reportDataType == ReportDataType.UNKNOWN) {
            log.error("Report data type not found");
            return;
        }
        /// 시간 통계 구하기
        try {
            hourlyProcess(reportDataType);
        } catch (Exception e){
            log.error("{} : error with hourly reports", e.getMessage());
            throw e;
        }

        try {
            dailyProcess(reportDataType);
        } catch (Exception e){
            log.error("{} : error with daily reports", e.getMessage());
            throw e;
        }
//
//        /// 일간 통계 구하기
//        Map<Long, List<DailyReportDto>> dailyReports = dailyReport(hourlyReports, reportDataType);
//        try {
//            //TODO : 맵을 평탄화 해서 보내줄 것임
////            dailyReportService.saveAll(dailyReports);
//        } catch (Exception e){
//            log.error("{} : error with daily reports", e.getMessage());
//        }
//
//        /// 월간 통계 구하기
//        Map<Long, List<MonthlyReportDto>> monthlyReports = monthlyReport(dailyReports);
//        try {
//            //TODO : 맵을 평탄화 해서 보내줄 것임
////            monthlyReportService.saveAll(monthlyReports);
//        } catch (Exception e){
//            log.error("{} : error with monthly reports", e.getMessage());
//        }
    }

    protected abstract Map<LocalDateTime, Map<Long, List<GenerationLogDto>>> getRawSource(LocalDateTime start, LocalDateTime end);

    protected abstract Map<LocalDate, Map<Long, List<HourlyReportDto>>> getHourlySource(LocalDateTime start, LocalDateTime end, int dayOffset);

    protected abstract Map<Long, List<DailyReportDto>> getDailySource(LocalDate start, LocalDate end);

    protected abstract List<HourlyReportDto> hourlyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType);

    protected abstract List<DailyReportDto> dailyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType);

    protected abstract List<MonthlyReportDto> monthlyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType);

    private void hourlyProcess(ReportDataType reportDataType) {
        /// 시작시간, 끝 시간을 구하기 위해 "마지막 생성 시간 중 가장 오래된 기록 가져오기"
        LocalDateTime defaultStartTime = LocalDateTime.now();
        LocalDateTime startTime = getStartTimeForHourly(defaultStartTime, reportDataType);
        LocalDateTime endTime = getEndTimeForHourly(startTime, reportDataType);

        if(startTime.isAfter(endTime)) {
            return;
        }

        List<HourlyReportDto> hourlyReports =
                hourlyReport(startTime, endTime, reportDataType);

        hourlyReportService.saveAll(hourlyReports);
    }

    private void dailyProcess(ReportDataType reportDataType) {
        LocalDateTime defaultStartTime = TimeTruncater.truncateToTerm(LocalDateTime.now(), StaticValues.DAY_SECONDS);
        LocalDateTime startTime = getStartTimeForDaily(defaultStartTime, reportDataType);
        LocalDateTime endTime = getEndTimeForDaily(startTime, defaultStartTime, reportDataType);

        if(startTime.isAfter(endTime)) {
            return;
        }

        List<DailyReportDto> dailyReportDtoList =
                dailyReport(startTime, endTime, reportDataType);

        dailyReportService.saveAll(dailyReportDtoList);
    }

    protected abstract LocalDateTime getEndTimeForDaily(LocalDateTime startTime, LocalDateTime defaultStartTime, ReportDataType reportDataType);

    protected abstract LocalDateTime getStartTimeForDaily(LocalDateTime defaultStartTime, ReportDataType reportDataType);

    private void monthlyProcess(List<MonthlyReportDto> monthlyReportDtoList) {
        monthlyReportService.saveAll(monthlyReportDtoList);
    }

    protected abstract LocalDateTime getStartTimeForHourly
            (LocalDateTime defaultTime, ReportDataType reportDataType);
    protected abstract LocalDateTime getEndTimeForHourly
            (LocalDateTime start, ReportDataType reportDataType);
}
