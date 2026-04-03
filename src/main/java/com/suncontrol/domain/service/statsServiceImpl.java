package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.statsChartDto;
import com.suncontrol.domain.dto.statsSummaryDto;
import com.suncontrol.domain.mapper.statsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class statsServiceImpl implements statsService {

    private final statsMapper statsMapper;

    @Override
    public List<statsSummaryDto> getStatsSummary(Long memberId, String selectedDate) {
        return statsMapper.selectStatsSummary(memberId, selectedDate);
    }

    @Override
    public List<statsChartDto> getGenerationTrend(Long memberId, String selectedDate) {
        return statsMapper.selectGenerationTrend(memberId, selectedDate);
    }

    @Override
    public List<statsChartDto> getWeatherEfficiency(Long memberId, String selectedDate) {
        return statsMapper.selectWeatherEfficiency(memberId, selectedDate);
    }
}
