package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.statsChartDto;
import com.suncontrol.domain.dto.statsSummaryDto;

import java.util.List;

public interface statsService {

    List<statsSummaryDto> getStatsSummary(Long memberId, String selectedDate);

    List<statsChartDto> getGenerationTrend(Long memberId, String selectedDate);

    List<statsChartDto> getWeatherEfficiency(Long memberId, String selectedDate);
}