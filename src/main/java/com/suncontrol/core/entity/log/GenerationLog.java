package com.suncontrol.core.entity.log;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GenerationLog {
    private Long id;
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치
    private String weatherCode;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
