package com.suncontrol.core.dto.component;

import com.suncontrol.core.dto.asset.InverterDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InverterMeta {
    private final Long inverterId;
    private final Long plantId;
    private final BigDecimal capacity;
    private final LocalDateTime createdAt;

    public InverterMeta(InverterDto dto) {
        this.inverterId = dto.getInverterId();
        this.plantId = dto.getPlantId();
        this.capacity = dto.getCapacity();
        this.createdAt = dto.getCreatedAt();
    }
}
