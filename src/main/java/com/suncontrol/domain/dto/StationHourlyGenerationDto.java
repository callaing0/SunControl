package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StationHourlyGenerationDto {

    private String hourLabel;              // 예: 09:00
    private BigDecimal totalGeneration;    // 해당 시간대 총 발전량
}