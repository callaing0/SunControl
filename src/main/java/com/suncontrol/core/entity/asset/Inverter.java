package com.suncontrol.core.entity.asset;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Inverter {
    private Long id;
    private Long plantId;
    private String serial;
    private BigDecimal ratedCapacity;
    private BigDecimal efficiency;
    private BigDecimal measuredCapacity;
    private BigDecimal currentPower;
    private BigDecimal lastAccumEnergy;
    private String type;
    private int statusCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
