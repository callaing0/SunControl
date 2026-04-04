package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.dto.asset.component.InverterBaseDto;
import com.suncontrol.core.entity.asset.Inverter;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InverterGenerationDto extends InverterBaseDto {
    private final DeviceStatus status;
    private final BigDecimal currentPower;
    private final BigDecimal efficiency;
    private final BigDecimal ratedCapacity;
    private final BigDecimal measuredCapacity;
    private final BigDecimal lastAccumEnergy;

    public InverterGenerationDto(Inverter entity) {
        super(entity.getPlantId(), entity.getId());
        this.status = entity.getStatus();
        this.currentPower = entity.getCurrentPower();
        this.efficiency = entity.getEfficiency();
        this.ratedCapacity = entity.getRatedCapacity();
        this.measuredCapacity = entity.getMeasuredCapacity();
        this.lastAccumEnergy = entity.getLastAccumEnergy();
    }

    public Long getId() {
        return super.getId();
    }

    public Long getPlantId() {
        return super.getPlantId();
    }
}
