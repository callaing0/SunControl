package com.suncontrol.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class chartDto {
    private String label;
    private BigDecimal value;
}