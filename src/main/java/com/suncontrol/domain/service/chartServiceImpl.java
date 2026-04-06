package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.dto.chartSummaryDto;
import com.suncontrol.core.repository.chartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class chartServiceImpl implements chartService {

    private final chartRepository chartRepository;

    @Override
    public List<chartSummaryDto> getStatsSummary(Long memberId, String selectedDate) {
        return chartRepository.selectStatsSummary(memberId, selectedDate);
    }

    @Override
    public List<chartDto> getGenerationTrend(Long memberId, String selectedDate) {
        return chartRepository.selectGenerationTrend(memberId, selectedDate);
    }

    @Override
    public List<chartDto> getWeatherEfficiency(Long memberId, String selectedDate) {
        return chartRepository.selectWeatherEfficiency(memberId, selectedDate);
    }
}
