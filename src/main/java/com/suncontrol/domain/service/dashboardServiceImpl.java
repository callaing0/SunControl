package com.suncontrol.domain.service;

import com.suncontrol.core.repository.report.dashboardRepository;
import com.suncontrol.domain.dto.DashboardGenerationDto;
import com.suncontrol.domain.dto.DashboardHourlyValueDto;
import com.suncontrol.domain.dto.DashboardInverterDto;
import com.suncontrol.domain.dto.DashboardPlantDto;
import com.suncontrol.domain.dto.DashboardRealtimeDto;
import com.suncontrol.domain.dto.DashboardSunTimeDto;
import com.suncontrol.domain.dto.dashboardSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class dashboardServiceImpl implements dashboardService {

    private static final BigDecimal UNIT_PRICE = new BigDecimal("201");
    private static final BigDecimal CO2_FACTOR = new BigDecimal("0.441");
    private static final BigDecimal TREE_DIVISOR = new BigDecimal("6.6");

    private final dashboardRepository dashboardRepository;

    @Override
    public dashboardSummaryDto getDashboardSummary(Long memberId, Long plantId) {
        DashboardPlantDto plant = dashboardRepository.selectPlantById(memberId, plantId);

        if (plant == null) {
            return null;
        }

        Long targetPlantId = plant.getPlantId();

        // 🔥 인버터 존재 여부 체크
        List<DashboardInverterDto> inverterList =
                dashboardRepository.selectInvertersByPlant(memberId, targetPlantId);

        boolean hasInverters = inverterList != null && !inverterList.isEmpty();

        dashboardSummaryDto summary = new dashboardSummaryDto();

        summary.setPlantId(targetPlantId);
        summary.setPlantName(plant.getPlantName());
        summary.setLocation(plant.getLocation());

        // 🔥 인버터 없으면 여기서 끝
        if (!hasInverters) {
            summary.setCurrentPower(BigDecimal.ZERO);
            summary.setEfficiency(BigDecimal.ZERO);
            summary.setDailyAccumulation(BigDecimal.ZERO);
            summary.setPredGen(BigDecimal.ZERO);
            summary.setInsolation(BigDecimal.ZERO);
            summary.setSunTime("--");
            summary.setWeatherStatus("--");

            summary.setUnitPrice(UNIT_PRICE);
            summary.setTotalProfit(BigDecimal.ZERO);
            summary.setCo2Reduction(BigDecimal.ZERO);
            summary.setTreeCount(0);

            summary.setChartLabels(List.of());
            summary.setPowerList(List.of());
            summary.setInsolationList(List.of());

            return summary;
        }

        DashboardRealtimeDto realtime = dashboardRepository.selectPlantRealtime(targetPlantId);
        DashboardSunTimeDto sunTime = dashboardRepository.selectTodaySunTime(plant.getDistrictCode());
        BigDecimal temperature = dashboardRepository.selectLatestTemperature(plant.getDistrictCode());
        BigDecimal insolation = dashboardRepository.selectTodayInsolation(targetPlantId);
        DashboardGenerationDto generation = dashboardRepository.selectTodayGeneration(targetPlantId);
        BigDecimal totalGeneration = dashboardRepository.selectTotalGeneration(targetPlantId);

        summary.setPlantId(targetPlantId);
        summary.setPlantName(plant.getPlantName());
        summary.setLocation(plant.getLocation());

        summary.setDailyAccumulation(nvl(generation != null ? generation.getDailyAccumulation() : null));
        summary.setPredGen(nvl(generation != null ? generation.getPredGen() : null));

        summary.setInsolation(nvl(insolation));
        summary.setSunTime(formatSunTime(sunTime));
        summary.setWeatherStatus(formatTemperature(temperature));

        summary.setUnitPrice(UNIT_PRICE);

        // 수익예측 = 예상 발전량 X 판매 단가
        summary.setTotalProfit(summary.getPredGen()
                                .multiply(UNIT_PRICE)
                                .setScale(2, RoundingMode.HALF_UP));

        // 환경기여도 = 전체 누적 발전량 기준
        BigDecimal co2Reduction = summary.getDailyAccumulation()
                .multiply(CO2_FACTOR)
                .setScale(2, RoundingMode.HALF_UP);

        summary.setCo2Reduction(co2Reduction);
        summary.setTreeCount(
                co2Reduction.divide(TREE_DIVISOR, 0, RoundingMode.DOWN).intValue()
        );

        List<DashboardHourlyValueDto> powerRows = dashboardRepository.selectHourlyPower(targetPlantId);
        List<DashboardHourlyValueDto> insolationRows = dashboardRepository.selectHourlyInsolation(targetPlantId);

        boolean hasInsolationData = insolationRows != null && !insolationRows.isEmpty();

        int startHour = 6;
        int endHour = 18;

        // 🔥 출력량 / 효율 제어
        if (!hasInsolationData) {
            summary.setCurrentPower(BigDecimal.ZERO);
            summary.setEfficiency(BigDecimal.ZERO);
        } else {
            summary.setCurrentPower(nvl(realtime != null ? realtime.getCurrentPower() : null));
            summary.setEfficiency(nvl(realtime != null ? realtime.getEfficiency() : null));
        }

        // 🔥 차트 제어
        if (!hasInsolationData) {
            summary.setChartLabels(List.of());
            summary.setPowerList(List.of());
            summary.setInsolationList(List.of());
        } else {
            summary.setChartLabels(buildChartLabels());
            summary.setPowerList(buildHourlySeries(powerRows, startHour, endHour));
            summary.setInsolationList(buildHourlySeries(insolationRows, startHour, endHour));
        }

        return summary;
    }

    @Override
    public List<DashboardInverterDto> getInverters(Long memberId, Long plantId) {
        return dashboardRepository.selectInvertersByPlant(memberId, plantId);
    }

    private List<String> buildChartLabels() {
        List<String> labels = new ArrayList<>();
        for (int hour = 6; hour <= 18; hour++) {
            labels.add(String.format("%02d시", hour));
        }
        return labels;
    }

    private List<BigDecimal> buildHourlySeries(List<DashboardHourlyValueDto> source, int startHour, int endHour) {
        List<BigDecimal> result = new ArrayList<>();
        int currentHour = java.time.LocalTime.now().getHour();

        for (int hour = startHour; hour <= endHour; hour++) {
            result.add(null);
        }

        if (source == null) {
            return result;
        }

        for (DashboardHourlyValueDto row : source) {
            if (row == null || row.getHour() == null || row.getValue() == null) {
                continue;
            }

            int hour = row.getHour();

            if (hour < startHour || hour > endHour) {
                continue;
            }

            // 현재 시각 이후 데이터는 표시하지 않음
            if (hour > currentHour) {
                continue;
            }

            result.set(hour - startHour, row.getValue());
        }

        return result;
    }

    private BigDecimal nvl(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private String formatSunTime(DashboardSunTimeDto sunTime) {
        if (sunTime == null) {
            return "--";
        }

        LocalDateTime sunrise = sunTime.getSunrise();
        LocalDateTime sunset = sunTime.getSunset();

        if (sunrise == null || sunset == null) {
            return "--";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return sunrise.format(formatter) + " / " + sunset.format(formatter);
    }

    private String formatTemperature(BigDecimal temperature) {
        if (temperature == null) {
            return "--";
        }

        return temperature.setScale(1, RoundingMode.HALF_UP).toPlainString() + "°C";
    }

    public String getLastUpdateTime() {
        LocalDateTime last = dashboardRepository.selectLastUpdateTime();
        return last != null
                ? last.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                : "--:--:--";
    }
}