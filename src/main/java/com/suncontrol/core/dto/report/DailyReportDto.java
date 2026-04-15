package com.suncontrol.core.dto.report;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.dto.component.StoppedDto;
import com.suncontrol.core.entity.report.DailyReport;
import com.suncontrol.core.util.SafeDivider;
import com.suncontrol.core.util.TimeTruncater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class DailyReportDto {
    private Long inverterId;
    private LocalDate baseDate;
    private ReportDataType reportDataType;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal valuePrevious; /// 전날 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치
    private Weather weather;
    private Integer weatherCode;
    private int stoppedTime; /// 가동정지 시간(초)
    private int incidentCount; /// 가동정지 회수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Integer getDayOffset() {
        return this.reportDataType.getDayOffset();
    }

    public DailyReportDto(DailyReport entity) {
        this.inverterId = entity.getInverterId();
        this.baseDate = entity.getBaseDate();
        this.reportDataType = ReportDataType.findByDayOffset(entity.getDayOffset());
        this.valueExpected = entity.getValueExpected();
        this.valueActual = entity.getValueActual();
        this.valuePrevious = entity.getValuePrevious();
        this.performanceRatio = entity.getPerformanceRatio();
        this.expectedRatio = entity.getExpectedRatio();
        this.capacityFactor = entity.getCapacityFactor();
        this.accumEnergy = entity.getAccumEnergy();
        this.weather = Weather.fromCode(entity.getWeatherCode());
        this.weatherCode = entity.getWeatherCode();
        this.stoppedTime = entity.getStoppedTime();
        this.incidentCount = entity.getIncidentCount();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public GenerationValuesDto getValuesDto() {
        return new GenerationValuesDto(
                inverterId, baseDate.atStartOfDay(), valueExpected, valueActual, valuePrevious,
                accumEnergy, performanceRatio, GenerationStatus.PENDING
        );
    }

    public DailyReportDto(
            GenerationValuesDto dto,
            BigDecimal capacity,
            Integer weatherCode,
            ReportDataType reportDataType,
            StoppedDto stoppedDto
    ) {
        this.inverterId = dto.getInverterId();
        this.baseDate = dto.getBaseTime().toLocalDate();
        this.reportDataType = reportDataType;
        this.weatherCode = weatherCode;

        this.valueActual = dto.getValueActual();
        this.valueExpected = dto.getValueExpected();
        this.valuePrevious = dto.getValuePrevious();
        this.accumEnergy = dto.getAccumEnergy();

        this.performanceRatio = dto.getPerformanceRatio();

        BigDecimal capacityDailyTotal = capacity.multiply(BigDecimal.valueOf(24));
        this.expectedRatio = SafeDivider.ratioDivide(valueExpected, capacityDailyTotal);
        this.capacityFactor = SafeDivider.ratioDivide(valueExpected, capacityDailyTotal);

        this.stoppedTime = stoppedDto.getStoppedTime();
        this.incidentCount = stoppedDto.getIncidentCount();
    }

    public StoppedDto getStoppedDto() {
        return new StoppedDto(stoppedTime, incidentCount);
    }

    public String getBaseMonth() {
        return TimeTruncater.getBaseMonth(baseDate);
    }
}