package com.suncontrol.domain.service;

import com.suncontrol.core.repository.dashboardRepository;
import com.suncontrol.domain.dto.dashboardSummaryDto;
import com.suncontrol.domain.dto.inverterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class dashboardServiceImpl implements dashboardService {

    private final dashboardRepository dashboardRepository;

    @Override
    public dashboardSummaryDto getDashboardSummary(Long memberId) {
        dashboardSummaryDto summary = dashboardRepository.selectDashboardSummary(memberId);

        if (summary == null) {
            return null;
        }

        Long plantId = summary.getPlantId();
        if (plantId == null) {
            return summary;
        }

        List<BigDecimal> powerList = dashboardRepository.selectHourlyPower(plantId);
        List<BigDecimal> insolationList = dashboardRepository.selectHourlyInsolation(plantId);

        summary.setChartLabels(buildChartLabels());
        summary.setPowerList(normalizeSeries(powerList, 13));
        summary.setInsolationList(normalizeSeries(insolationList, 13));

        return summary;
    }

    @Override
    public List<inverterDto> getInverters(Long memberId) {
        return dashboardRepository.selectInverters(memberId);
    }

    private List<String> buildChartLabels() {
        List<String> labels = new ArrayList<>();
        for (int hour = 6; hour <= 18; hour++) {
            labels.add(String.format("%02d시", hour));
        }
        return labels;
    }

    private List<BigDecimal> normalizeSeries(List<BigDecimal> source, int size) {
        List<BigDecimal> result = new ArrayList<>();

        if (source != null) {
            result.addAll(source);
        }

        while (result.size() < size) {
            result.add(BigDecimal.ZERO);
        }

        if (result.size() > size) {
            return result.subList(0, size);
        }

        return result;
    }
}