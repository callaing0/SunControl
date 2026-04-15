package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.MonthlyReportDto;
import com.suncontrol.core.entity.report.MonthlyReport;
import com.suncontrol.core.repository.report.MonthlyReportRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import com.suncontrol.core.util.TimeTruncater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonthlyReportService {
    private final MonthlyReportRepository repository;

    public void saveAll(List<MonthlyReportDto> dtoList) {
        if(dtoList==null || dtoList.isEmpty()){
            log.warn("no monthly report generated");
            return;
        }
        int result = repository.saveAll(DataCollectorsUtil.toDataList(dtoList, MonthlyReport::new));
        log.info("save monthly report result: {}", result);
    }

    public List<MonthlyReportDto> findAllByMonthBetween(LocalDate start, LocalDate end) {
        String startMonth = TimeTruncater.getBaseMonth(start);
        String endMonth = TimeTruncater.getBaseMonth(end);

        return entityToDto(repository.findAllByMonthBetween(startMonth, endMonth));
    }

    public List<MonthlyReportDto> findAllLatestByInverter() {

        return entityToDto(repository.findAllLatestByInverter());
    }

    private List<MonthlyReportDto> entityToDto(List<MonthlyReport> entities) {
        if(entities==null || entities.isEmpty()){
            log.warn("no monthly report found");
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, MonthlyReportDto::new);
    }
}
