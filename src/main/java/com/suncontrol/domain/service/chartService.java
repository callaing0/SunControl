package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.dto.chartSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface chartService {

    List<chartSummaryDto> getStatsSummary(Long memberId, LocalDate selectedDate);

    List<chartDto> getGenerationTrend(Long memberId, LocalDate selectedDate);

    List<chartDto> getWeatherEfficiency(Long memberId, LocalDate selectedDate);
}