package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.dto.chartSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface chartService {

    List<chartSummaryDto> getStatsSummary(Long plantId, LocalDate selectedDate);

    List<chartDto> getGenerationTrend(Long plantId, LocalDate selectedDate);

    List<chartDto> getWeatherEfficiency(Long plantId, LocalDate selectedDate);

    List<chartSummaryDto> getMonthlySummary(Long plantId, String month);

    List<chartDto> getMonthlyTrend(Long plantId);

    List<chartDto> getMonthlyWeather(Long plantId, String month);
}