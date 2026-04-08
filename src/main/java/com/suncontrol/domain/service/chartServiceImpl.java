package com.suncontrol.domain.service;

import com.suncontrol.core.repository.report.chartRepository;
import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.dto.chartSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class chartServiceImpl implements chartService {

    private final chartRepository chartRepository;

    @Override
    public List<chartSummaryDto> getStatsSummary(Long plantId, LocalDate selectedDate) {
        BigDecimal totalGeneration = nvl(chartRepository.selectTotalGeneration(plantId, selectedDate));
        BigDecimal expectedGeneration = nvl(chartRepository.selectExpectedGeneration(plantId, selectedDate));
        BigDecimal averageEfficiency = nvl(chartRepository.selectAverageEfficiency(plantId, selectedDate));
        int inverterCount = chartRepository.selectInverterCount(plantId);
        int alertCount = chartRepository.selectAlertCount(plantId, selectedDate);

        List<chartSummaryDto> summaryList = new ArrayList<>();
        summaryList.add(createSummary("총 발전량", totalGeneration.stripTrailingZeros().toPlainString() + " kWh"));
        summaryList.add(createSummary("예상 발전량", expectedGeneration.stripTrailingZeros().toPlainString() + " kWh"));
        summaryList.add(createSummary("평균 효율", averageEfficiency.stripTrailingZeros().toPlainString() + " %"));
        summaryList.add(createSummary("인버터 수", inverterCount + " 대"));
        summaryList.add(createSummary("장애 건수", alertCount + " 건"));

        return summaryList;
    }

    @Override
    public List<chartDto> getGenerationTrend(Long plantId, LocalDate selectedDate) {
        return chartRepository.selectGenerationTrend(plantId, selectedDate);
    }

    @Override
    public List<chartDto> getWeatherEfficiency(Long plantId, LocalDate selectedDate) {
        return chartRepository.selectWeatherEfficiency(plantId, selectedDate);
    }

    private chartSummaryDto createSummary(String title, String value) {
        chartSummaryDto dto = new chartSummaryDto();
        dto.setTitle(title);
        dto.setValue(value);
        return dto;
    }

    private BigDecimal nvl(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}