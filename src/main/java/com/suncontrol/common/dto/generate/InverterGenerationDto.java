package com.suncontrol.common.dto.generate;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.asset.InverterType;
import com.suncontrol.core.dto.asset.InverterDto;
import com.suncontrol.core.dto.asset.component.InverterBaseDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InverterGenerationDto extends InverterBaseDto {
    private final DeviceStatus status;
    private final BigDecimal currentPower;
    private final BigDecimal efficiency;
    private final BigDecimal ratedCapacity;
    private final BigDecimal measuredCapacity;
    private final BigDecimal lastAccumEnergy;
    private final InverterType inverterType;
    private final LocalDateTime createdAt;

    public InverterGenerationDto(InverterDto dto) {
        super(dto.getPlantId(), dto.getId());
        this.status = dto.getStatus();
        this.currentPower = dto.getCurrentPower();
        this.efficiency = dto.getEfficiency();
        this.ratedCapacity = dto.getRatedCapacity();
        this.measuredCapacity = dto.getMeasuredCapacity();
        this.lastAccumEnergy = dto.getLastAccumEnergy();
        this.inverterType = dto.getInverterType();
        this.createdAt = dto.getCreatedAt();
    }

    public Long getId() {
        return super.getId();
    }

    public Long getPlantId() {
        return super.getPlantId();
    }
}
