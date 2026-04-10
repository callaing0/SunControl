package com.suncontrol.core.service.report;

import com.suncontrol.core.repository.report.HourlyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HourlyReportService {
    private final HourlyReportRepository repository;
}
