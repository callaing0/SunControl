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
        BigDecimal previousGeneration = nvl(chartRepository.selectPreviousGeneration(plantId, selectedDate));
        BigDecimal averageGeneration = nvl(chartRepository.selectAverageGeneration(plantId, selectedDate));
        BigDecimal expectedGeneration = nvl(chartRepository.selectExpectedGeneration(plantId, selectedDate));
        Integer stoppedTime = chartRepository.selectStoppedTime(plantId, selectedDate);

        BigDecimal changeRate = BigDecimal.ZERO;

        if (previousGeneration.compareTo(BigDecimal.ZERO) > 0) {
            changeRate = totalGeneration.subtract(previousGeneration)
                    .divide(previousGeneration, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        System.out.println("selectedDate = " + selectedDate);
        System.out.println("totalGeneration = " + totalGeneration);
        System.out.println("previousGeneration = " + previousGeneration);
        System.out.println("changeRate = " + changeRate);

        List<chartSummaryDto> summaryList = new ArrayList<>();

        summaryList.add(createSummary("기간 누적 발전량",
                totalGeneration.stripTrailingZeros().toPlainString() + " kWh"));

        summaryList.add(createSummary("대비 증감률",
                changeRate.setScale(2, BigDecimal.ROUND_HALF_UP) + " %"));

        summaryList.add(createSummary("평균 발전량",
                averageGeneration.stripTrailingZeros().toPlainString() + " kWh"));

        summaryList.add(createSummary("기상보정기대값",
                expectedGeneration.stripTrailingZeros().toPlainString() + " kWh"));

        summaryList.add(createSummary("인버터 가동 중지 시간",
                (stoppedTime == null ? 0 : stoppedTime) + " sec"));

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