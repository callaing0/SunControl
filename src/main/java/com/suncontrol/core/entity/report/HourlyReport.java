package com.suncontrol.core.entity.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private String  weatherCode;
    private int dayOffset; /// 0: 당일 1~7:N일전 예측 표시
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
