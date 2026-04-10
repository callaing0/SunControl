package com.suncontrol.core.service.report;

import com.suncontrol.core.repository.report.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyReportService {
    private final DailyReportRepository repository;
}
