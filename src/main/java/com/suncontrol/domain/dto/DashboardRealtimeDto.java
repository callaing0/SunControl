package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardRealtimeDto {
    private Long plantId;
    private BigDecimal currentPower;
    private BigDecimal efficiency;
}