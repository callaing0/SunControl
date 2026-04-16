package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.entity.report.DailyReport;
import com.suncontrol.core.repository.report.DailyReportRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyReportService {
    private final DailyReportRepository repository;

    @Transactional
    public void saveAll(List<DailyReportDto> dailyReportDtoList) {
        if(dailyReportDtoList == null || dailyReportDtoList.isEmpty()) {
            log.warn("no daily reports found");
            return;
        }

        int result = repository.saveAll(
                DataCollectorsUtil.toDataList(
                        dailyReportDtoList,
                        DailyReport::new
                ));
        log.info("Save {} daily reports to DB", result);
    }

    public List<DailyReportDto> findAllLatestByInverter(Integer dayOffset) {
        List<DailyReport> entities = repository.findAllLatestByInverter(dayOffset);

        if(entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, DailyReportDto::new);
    }

    public List<DailyReportDto> findAllByDateBetween(LocalDate start, LocalDate end, Integer dayOffset) {
        List<DailyReport> entities = repository.findAllByDateBetween(start, end, dayOffset);

        if(entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, DailyReportDto::new);
    }


    public List<DailyReportDto> findAllByDateBetween(Long id,LocalDate start, LocalDate end, Integer dayOffset) {
        List<DailyReport> entities = repository.findAllByDateBetween(start, end, dayOffset);

        if(entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, DailyReportDto::new);
    }
}
