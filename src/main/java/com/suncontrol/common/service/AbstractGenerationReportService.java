package com.suncontrol.common.service;

import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.generic.InverterIdProvider;
import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.report.*;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.report.*;
import com.suncontrol.core.util.DataCollectorsUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final DailyWeatherService dailyWeatherService;
    private final GenerationEnergyService generationEnergyService;

    protected final int HOUR_SECONDS = 3600;
    protected final int DAY_SECONDS = 86400;

    /// 통계 작성하는 프로세스 메서드
    /// 파라미터 예시 ) ReportDataType.ACTUAL_SNAPSHOT 등
    @Transactional
    public void process(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType){
        /// 잘못된 파라미터 요청이 들어오면 뱉어버린다.
        if(reportDataType == ReportDataType.UNKNOWN || start.isAfter(end)) {
            log.error("Report data type not found or bad Request");
            return;
        }
        /// 시간 통계 구하기
        try {
            List<HourlyReportDto> hourlyReports = hourlyReport(start, end, reportDataType);
            //TODO : 저장로직은 구현체 메서드 안에서 호출할 예정임
//            hourlyReportService.saveAll(DataCollectorsUtil.flatMapping(hourlyReports));
        } catch (Exception e){
            log.error("{} : error with hourly reports", e.getMessage());
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

    protected abstract Map<Long, Map<LocalDateTime, GenerationLogDto>> getRawSource(LocalDateTime start, LocalDateTime end);

    protected abstract Map<Long, List<HourlyReportDto>> getHourlySource(LocalDateTime start, LocalDateTime end);

    protected abstract Map<Long, List<DailyReportDto>> getDailySource(LocalDate start, LocalDate end);

    @Transactional
    protected abstract List<HourlyReportDto> hourlyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType);

    @Transactional
    protected abstract List<DailyReportDto> dailyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType);

    @Transactional
    protected abstract List<MonthlyReportDto> monthlyReport(LocalDateTime start, LocalDateTime end, ReportDataType reportDataType);

    @Transactional
    protected void saveHourly(List<HourlyReportDto> hourlyReportDtoList) {
        hourlyReportService.saveAll(hourlyReportDtoList);
    }

    @Transactional
    protected void saveDaily(List<DailyReportDto> dailyReportDtoList) {
        dailyReportService.saveAll(dailyReportDtoList);
    }

    @Transactional
    protected void saveMonthly(List<MonthlyReportDto> monthlyReportDtoList) {
        monthlyReportService.saveAll(monthlyReportDtoList);
    }
}
