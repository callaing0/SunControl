package com.suncontrol.core.entity.report;

import com.suncontrol.core.dto.report.HourlyReportDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HourlyReport {
    private Long id;
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal valuePrevious; /// 전날 동시간 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치
    private Integer weatherCode;
    private Integer dayOffset; /// 0: 당일 1~7:N일전 예측 표시
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HourlyReport(HourlyReportDto dto) {
        this.inverterId = dto.getInverterId();
        this.baseTime = dto.getBaseTime();
        this.valueExpected = dto.getValueExpected();
        this.valueActual = dto.getValueActual();
        this.valuePrevious = dto.getValuePrevious();
        this.performanceRatio = dto.getPerformanceRatio();
        this.expectedRatio = dto.getExpectedRatio();
        this.capacityFactor = dto.getCapacityFactor();
        this.accumEnergy = dto.getAccumEnergy();
        this.weatherCode = dto.getWeatherCode();
        this.dayOffset = dto.getReportDataType().getDayOffset();
        this.status = dto.getGenerationStatus().getStatus();
    }
}
