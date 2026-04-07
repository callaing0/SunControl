package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.asset.InverterType;
import com.suncontrol.core.dto.asset.component.InverterBaseDto;
import com.suncontrol.core.entity.asset.Inverter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class InverterDto extends InverterBaseDto {
    private String serial;
    private BigDecimal ratedCapacity;
    private BigDecimal efficiency;
    private BigDecimal measuredCapacity;
    private BigDecimal currentPower;
    private BigDecimal lastAccumEnergy;
    private InverterType inverterType;
    private DeviceStatus status;
    private LocalDateTime createdAt;

    public InverterDto(Inverter inverter) {
        super(inverter.getId(), inverter.getPlantId());
        this.serial = inverter.getSerial();
        this.ratedCapacity = inverter.getRatedCapacity();
        this.efficiency = inverter.getEfficiency();
        this.measuredCapacity = inverter.getMeasuredCapacity();
        this.currentPower = inverter.getCurrentPower();
        this.lastAccumEnergy = inverter.getLastAccumEnergy();
        this.inverterType = inverter.getInverterType();
        this.status = inverter.getStatus();
        this.createdAt = inverter.getCreatedAt();
    }
}
