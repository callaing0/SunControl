package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardGenerationDto {
    private Long plantId;
    private BigDecimal dailyAccumulation;
    private BigDecimal predGen;
}