package com.suncontrol.core.dto.report;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.entity.report.DailyReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private int stoppedTime; /// 가동정지 시간(초)
    private int incidentCount; /// 가동정지 회수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Integer getDayOffset() {
        return this.reportDataType.getDayOffset();
    }
    public Integer getWeatherCode() {
        return this.weather.getWeatherCode();
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
        this.stoppedTime = entity.getStoppedTime();
        this.incidentCount = entity.getIncidentCount();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}