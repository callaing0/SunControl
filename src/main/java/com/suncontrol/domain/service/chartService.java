package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.dto.chartSummaryDto;

import java.util.List;

public interface chartService {

    List<chartSummaryDto> getStatsSummary(Long memberId, String selectedDate);

    List<chartDto> getGenerationTrend(Long memberId, String selectedDate);

    List<chartDto> getWeatherEfficiency(Long memberId, String selectedDate);
}