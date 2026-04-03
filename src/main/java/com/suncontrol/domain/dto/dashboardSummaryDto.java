package com.suncontrol.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class dashboardSummaryDto {
    private Long plantId;

    private BigDecimal currentPower;
    private BigDecimal dailyAccumulation;
    private BigDecimal efficiency;

    private String plantName;
    private String location;
    private String sunTime;
    private BigDecimal insolation;
    private String weatherStatus;

    private BigDecimal totalProfit;
    private BigDecimal predGen;
    private BigDecimal unitPrice;

    private BigDecimal co2Reduction;
    private Integer treeCount;

    private List<String> chartLabels;
    private List<BigDecimal> powerList;
    private List<BigDecimal> insolationList;
}
