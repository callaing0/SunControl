package com.suncontrol.common.service;

import com.suncontrol.core.constant.util.ReportDataType;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActualGenerationReportService extends AbstractGenerationReportService{

    public ActualGenerationReportService(GenerationLogService generationLogService, HourlyReportService hourlyReportService, DailyReportService dailyReportService, MonthlyReportService monthlyReportService, PlantService plantService, DailyWeatherService dailyWeatherService, GenerationEnergyService generationEnergyService) {
        super(generationLogService, hourlyReportService, dailyReportService, monthlyReportService, plantService, dailyWeatherService, generationEnergyService);
    }

    @Override
    protected Map<Long, List<GenerationLogDto>> getSource(ReportDataType reportDataType) {
        return Map.of();
    }

    @Override
    protected Map<Long, List<HourlyReportDto>> hourlyReport(Map<Long, List<GenerationLogDto>> generationLogs, ReportDataType reportDataType) {
        return Map.of();
    }

    @Override
    protected Map<Long, List<DailyReportDto>> dailyReport(Map<Long, List<HourlyReportDto>> hourlyReports, ReportDataType reportDataType) {
        return Map.of();
    }

    @Override
    protected Map<Long, List<MonthlyReportDto>> monthlyReport(Map<Long, List<DailyReportDto>> dailyReports) {
        return Map.of();
    }
}
