package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MonthlyCompareChartDto { //전월 대비 증감률 차트 DTO
    private BigDecimal currentActual;   // 당월
    private BigDecimal previousActual;  // 전월
    private BigDecimal targetActual;    // 목표
    private BigDecimal increaseRate;    // 전월 대비 %
}