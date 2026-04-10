package com.suncontrol.core.dto.report;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.generic.BaseTimeProvider;
import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.constant.generic.InverterIdProvider;
import com.suncontrol.core.entity.report.HourlyReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HourlyReportDto implements InverterIdProvider, BaseTimeProvider {
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected;
    private BigDecimal valueActual;
    private BigDecimal valuePrevious;
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치
    private Integer weatherCode;
    private Weather weather;
    private ReportDataType reportDataType;
    private GenerationStatus generationStatus;

    public HourlyReportDto(HourlyReport entity) {
        this.inverterId = entity.getInverterId();
        this.baseTime = entity.getBaseTime();
        this.valueExpected = entity.getValueExpected();
        this.valueActual = entity.getValueActual();
        this.valuePrevious = entity.getValuePrevious();
        this.performanceRatio = entity.getPerformanceRatio();
        this.expectedRatio = entity.getExpectedRatio();
        this.capacityFactor = entity.getCapacityFactor();
        this.accumEnergy = entity.getAccumEnergy();
        this.weatherCode = entity.getWeatherCode();
        this.weather = Weather.fromCode(entity.getWeatherCode());
        this.reportDataType = ReportDataType.findByDayOffset(entity.getDayOffset());
        this.generationStatus = GenerationStatus.fromStatus(entity.getStatus());
    }

    public HourlyReportDto(Long inverterId, LocalDateTime baseTime, HourlyReportDto previous, ReportDataType reportDataType) {
        this.inverterId = inverterId;
        this.baseTime = baseTime;
        this.valuePrevious = previous != null ? previous.valueActual : null;
        this.reportDataType = reportDataType;
    }
}
