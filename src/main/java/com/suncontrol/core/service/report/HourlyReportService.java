package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.HourlyReportDto;
import com.suncontrol.core.entity.report.HourlyReport;
import com.suncontrol.core.repository.report.HourlyReportRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HourlyReportService {
    private final HourlyReportRepository repository;

    @Transactional
    public int saveAll(List<HourlyReportDto> dtoList) {
        return repository.saveAll(
                DataCollectorsUtil.toDataList(
                        dtoList,
                        HourlyReport::new
                ));
    }

    public List<HourlyReportDto> findAllByBaseTimeBetweenStartAndEnd
            (LocalDateTime start, LocalDateTime end, int dayOffset) {
        return DataCollectorsUtil.toDataList(
                repository.findAllByBaseTimeBetween(start, end, dayOffset),
                HourlyReportDto::new
        );
    }

    public List<HourlyReportDto> findAllLatestByInverter(int dayOffset) {
        return DataCollectorsUtil.toDataList(
                repository.findAllLatestByInverter(dayOffset),
                HourlyReportDto::new
        );
    }
}
