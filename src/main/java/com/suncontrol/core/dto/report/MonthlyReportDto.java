package com.suncontrol.core.dto.report;

import com.suncontrol.core.entity.report.MonthlyReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyReportDto {
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

    public MonthlyReportDto(MonthlyReport entity) {
        this.inverterId = entity.getInverterId();
        this.baseMonth = entity.getBaseMonth();
        this.valueExpected = entity.getValueExpected();
        this.valueActual = entity.getValueActual();
        this.valuePrevious = entity.getValuePrevious();
        this.performanceRatio = entity.getPerformanceRatio();
        this.expectedRatio = entity.getExpectedRatio();
        this.capacityFactor = entity.getCapacityFactor();
        this.accumEnergy = entity.getAccumEnergy();
        this.stoppedTime = entity.getStoppedTime();
        this.incidentCount = entity.getIncidentCount();
    }
}
