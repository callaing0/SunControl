package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.HourlyReportDto;
import com.suncontrol.core.repository.report.HourlyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HourlyReportService {
    private final HourlyReportRepository repository;

    public int saveAll(List<HourlyReportDto> dtoList) {
        return 0;
    }

    public List<HourlyReportDto> findAllByBaseTimeBetweenStartAndEnd(LocalDateTime start, LocalDateTime end) {
        return List.of();
    }
}
