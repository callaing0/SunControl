package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.HourlyReportDto;
import com.suncontrol.core.entity.report.HourlyReport;
import com.suncontrol.core.repository.report.HourlyReportRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HourlyReportService {
    private final HourlyReportRepository repository;

    @Transactional
    public void saveAll(List<HourlyReportDto> dtoList) {
        if(dtoList == null || dtoList.isEmpty()) {
            log.warn("no hourly reports found");
            return;
        }

        int result = repository.saveAll(
                DataCollectorsUtil.toDataList(
                        dtoList,
                        HourlyReport::new
                ));
        log.info("Save {} hourly reports to DB", result);
    }

    public List<HourlyReportDto> findAllByBaseTimeBetweenStartAndEnd
            (LocalDateTime start, LocalDateTime end, int dayOffset) {
        List<HourlyReport> entities =
                repository.findAllByBaseTimeBetween(start, end, dayOffset);

        if(entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, HourlyReportDto::new);
    }

    public List<HourlyReportDto> findAllLatestByInverter(int dayOffset) {
        List<HourlyReport> entities = repository.findAllLatestByInverter(dayOffset);

        if(entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, HourlyReportDto::new);
    }
}
