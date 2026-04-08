package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardHourlyValueDto {
    private Integer hour;
    private BigDecimal value;
}