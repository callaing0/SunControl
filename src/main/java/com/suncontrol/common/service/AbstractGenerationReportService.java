package com.suncontrol.common.service;

import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.generic.InverterIdProvider;
import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.report.*;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.GenerationLogService;
import com.suncontrol.core.service.report.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /// 통계 작성하는 프로세스 메서드
    /// 파라미터 예시 ) ReportDataType.ACTUAL_SNAPSHOT 등
    @Transactional
    public void process(ReportDataType reportDataType){
        /// 잘못된 파라미터 요청이 들어오면 뱉어버린다.
        if(reportDataType == ReportDataType.UNKNOWN) {
            log.error("Report data type not found");
            return;
        }
        /// 재료구하기
        Map<Long, List<GenerationLogDto>> source = getSource(reportDataType);
        /// 시간 통계 구하기
        Map<Long, List<HourlyReportDto>> hourlyReports = hourlyReport(source, reportDataType);

        /// 일간 통계 구하기
        Map<Long, List<DailyReportDto>> dailyReports = dailyReport(hourlyReports, reportDataType);

        /// 월간 통계 구하기
        Map<Long, List<MonthlyReportDto>> monthlyReports = monthlyReport(dailyReports);

        // TODO : 저장하기
    }

    protected abstract Map<Long, List<GenerationLogDto>> getSource(ReportDataType reportDataType);

    @Transactional
    protected abstract Map<Long, List<HourlyReportDto>> hourlyReport(Map<Long, List<GenerationLogDto>> generationLogs, ReportDataType reportDataType);

    @Transactional
    protected abstract Map<Long, List<DailyReportDto>> dailyReport(Map<Long, List<HourlyReportDto>> hourlyReports, ReportDataType reportDataType);

    @Transactional
    protected abstract Map<Long, List<MonthlyReportDto>> monthlyReport(Map<Long, List<DailyReportDto>> dailyReports);

    /// 조회를 위해 생성했던 Map을 도메인 서비스로 보내기 전에 평탄화시켜주는 작업
    protected <T> List<T> flattingMap(Map<Long, List<T>> map) {
        return map.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    /// 도메인 서비스에서 받은 dto리스트를 InverterId 기준 리스트로 변환하는 작업
    protected <T extends InverterIdProvider> Map<Long, List<T>> mappingList(List<T> list) {
        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                InverterIdProvider::getInverterId
                        )
                );
    }
}
