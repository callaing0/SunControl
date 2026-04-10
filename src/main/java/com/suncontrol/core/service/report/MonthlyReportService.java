package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.MonthlyReportDto;
import com.suncontrol.core.repository.report.MonthlyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyReportService {
    private final MonthlyReportRepository repository;

    public void saveAll(List<MonthlyReportDto> monthlyReportDtoList) {
    }
}
