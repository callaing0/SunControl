package com.suncontrol.core.service.report;

import com.suncontrol.core.repository.report.MonthlyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyReportService {
    private final MonthlyReportRepository repository;
}
