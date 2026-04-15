package com.suncontrol.core.entity.report;

import com.suncontrol.core.dto.report.MonthlyReportDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyReport {
    private Long id;
    private Long inverterId;
    private String baseMonth;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal valuePrevious; /// 전 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치
    private int stoppedTime; /// 가동정지 시간(초)
    private int incidentCount; /// 가동정지 회수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MonthlyReport(MonthlyReportDto dto) {
        this.inverterId = dto.getInverterId();
        this.baseMonth = dto.getBaseMonth();
        this.valueExpected = dto.getValueExpected();
        this.valueActual = dto.getValueActual();
        this.valuePrevious = dto.getValuePrevious();
        this.performanceRatio = dto.getPerformanceRatio();
        this.expectedRatio = dto.getExpectedRatio();
        this.capacityFactor = dto.getCapacityFactor();
        this.accumEnergy = dto.getAccumEnergy();
        this.stoppedTime = dto.getStoppedTime();
        this.incidentCount = dto.getIncidentCount();
    }
}
