package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.repository.report.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyReportService {
    private final DailyReportRepository repository;

    public void saveAll(List<DailyReportDto> dailyReportDtoList) {
    }
}
