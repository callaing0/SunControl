package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.entity.report.DailyReport;
import com.suncontrol.core.repository.report.DailyReportRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import com.suncontrol.domain.dto.DailyGenerationChartDto;
import com.suncontrol.domain.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyReportService {
    private final DailyReportRepository repository;

    public void saveAll(List<DailyReportDto> dailyReportDtoList) {
        if(dailyReportDtoList == null || dailyReportDtoList.isEmpty()) {
            log.warn("no daily reports found");
            return;
        }

        int result = repository.saveAll(
                DataCollectorsUtil.toDataList(
                        dailyReportDtoList,
                        DailyReport::new
                ));
        log.info("Save {} daily reports to DB", result);
    }

    public List<DailyReportDto> findAllLatestByInverter(Integer dayOffset) {
        List<DailyReport> entities = repository.findAllLatestByInverter(dayOffset);

        if(entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, DailyReportDto::new);
    }

    public List<DailyReportDto> findAllByDateBetween(LocalDate start, LocalDate end, Integer dayOffset) {
        List<DailyReport> entities = repository.findAllByDateBetween(start, end, dayOffset);

        if(entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, DailyReportDto::new);
    }

    public List<DailyReportDto> findByPlantIdDateBetween(Long pId, LocalDate start, LocalDate end, Integer dayOffset) {

        List<DailyReport> entities = repository.findByPlantIdDateBetween(pId, start, end, dayOffset);

        if(entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, DailyReportDto::new);
    }


    public ReportDto makeMonthlyReportDto(Long pId, List<DailyReportDto> dailyReportDtoList) {
        ReportDto reportDto = new ReportDto();

        if (dailyReportDtoList == null || dailyReportDtoList.isEmpty()) {
            reportDto.setPlantId(pId);
            reportDto.setValueActual(BigDecimal.ZERO);
            reportDto.setIncreaseRate(BigDecimal.ZERO);
            reportDto.setPerformanceRatio(BigDecimal.ZERO);
            reportDto.setIncidentCount(0);
            reportDto.setStoppedTime(0.0);
            return reportDto;
        }

        List<DailyReportDto> filteredList = dailyReportDtoList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(DailyReportDto::getBaseDate))
                .toList();

        if (filteredList.isEmpty()) {
            reportDto.setPlantId(null);
            reportDto.setValueActual(BigDecimal.ZERO);
            reportDto.setIncreaseRate(BigDecimal.ZERO);
            reportDto.setPerformanceRatio(BigDecimal.ZERO);
            reportDto.setIncidentCount(0);
            reportDto.setStoppedTime(0.0);
            return reportDto;
        }

        DailyReportDto firstRow = filteredList.get(0);
        Long inverterId = firstRow.getInverterId();
        LocalDate baseDate = firstRow.getBaseDate();

        BigDecimal totalActual = dailyReportDtoList.stream()
                .filter(Objects::nonNull)
                .map(DailyReportDto::getValueActual)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpected = dailyReportDtoList.stream()
                .filter(Objects::nonNull)
                .map(DailyReportDto::getValueExpected)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalIncidentCount = dailyReportDtoList.stream()
                .filter(Objects::nonNull)
                .mapToInt(DailyReportDto::getIncidentCount)
                .sum();

        double totalStoppedTime = dailyReportDtoList.stream()
                .filter(Objects::nonNull)
                .mapToInt(DailyReportDto::getStoppedTime)
                .sum();
        BigDecimal previousMonthTotalActual = getPreviousMonthTotalActual(inverterId, baseDate);
        BigDecimal increaseRate = calculateIncreaseRate(totalActual, previousMonthTotalActual);
        BigDecimal performanceRatio = calculatePerformanceRatio(totalActual, totalExpected);

        reportDto.setPlantId(inverterId);
        reportDto.setValueActual(scale(totalActual));
        reportDto.setIncreaseRate(scale(increaseRate));
        reportDto.setPerformanceRatio(scale(performanceRatio));
        reportDto.setIncidentCount(totalIncidentCount);
        reportDto.setStoppedTime(totalStoppedTime);

        return reportDto;
    }

    private BigDecimal getPreviousMonthTotalActual(Long inverterId, LocalDate currentBaseDate) {
        if (inverterId == null || currentBaseDate == null) {
            return BigDecimal.ZERO;
        }

        LocalDate previousMonth = currentBaseDate.minusMonths(1);
        LocalDate startDate = previousMonth.withDayOfMonth(1);
        LocalDate endDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());

        BigDecimal result = repository.sumValueActualByInverterIdAndBaseDateBetween(
                inverterId,
                startDate,
                endDate
        );

        return result != null ? result : BigDecimal.ZERO;
    }
    private BigDecimal calculateIncreaseRate(BigDecimal totalActual, BigDecimal previousMonthTotalActual) {
        if (previousMonthTotalActual == null || previousMonthTotalActual.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return totalActual.subtract(previousMonthTotalActual)
                .multiply(BigDecimal.valueOf(100))
                .divide(previousMonthTotalActual, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePerformanceRatio(BigDecimal totalActual, BigDecimal totalExpected) {
        if (totalExpected == null || totalExpected.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return totalActual.multiply(BigDecimal.valueOf(100))
                .divide(totalExpected, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal scale(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public DailyGenerationChartDto getDailyChart(Long plantId, YearMonth month) {

        List<DailyReportDto> list =
                repository.findDailyReportListByMonth(plantId, month.toString());

        DailyGenerationChartDto dto = new DailyGenerationChartDto();

        if (list == null || list.isEmpty()) {
            return dto;
        }

        double sum = 0;
        double max = 0;
        int maxDay = 0;

        for (DailyReportDto d : list) {

            int day = d.getBaseDate().getDayOfMonth();
            double value = d.getValueActual() != null
                    ? d.getValueActual().doubleValue()
                    : 0.0;

            dto.getLabels().add(String.valueOf(day));
            dto.getValues().add(value);

            sum += value;

            if (value > max) {
                max = value;
                maxDay = day;
            }
        }

        dto.setAverageValue(Math.round((sum / list.size()) * 10.0) / 10.0);
        dto.setMaxValue(Math.round(max * 10.0) / 10.0);
        dto.setMaxDay(maxDay);

        return dto;
    }
}
