package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PerformanceGaugeDto { //목표 달성률 게이지 DTO
    private BigDecimal actualValue;       // 달성 kWh
    private BigDecimal remainValue;       // 잔여 kWh
    private BigDecimal targetValue;       // 목표 kWh
    private BigDecimal performanceRatio;  // 달성률 %
}
