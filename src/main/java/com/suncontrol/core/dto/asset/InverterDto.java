package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.asset.InverterType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class InverterDto {
    private Long id;
    private Long plantId;
    private String serial;
    private BigDecimal ratedCapacity;
    private BigDecimal efficiency;
    private BigDecimal measuredCapacity;
    private BigDecimal currentPower;
    private BigDecimal lastAccumEnergy;
    private InverterType inverterType;
    private DeviceStatus status;
}
