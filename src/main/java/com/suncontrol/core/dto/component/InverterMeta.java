package com.suncontrol.core.dto.component;

import com.suncontrol.core.dto.asset.InverterDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InverterMeta {
    private final long inverterId;
    private final BigDecimal capacity;
    private final LocalDateTime createdAt;

    public InverterMeta(InverterDto dto) {
        this.inverterId = dto.getInverterId();
        this.capacity = dto.getCapacity();
        this.createdAt = dto.getCreatedAt();
    }
}
