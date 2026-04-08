package com.suncontrol.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class chartDto { //차트 데이터용
    private String label;
    private BigDecimal value;
}