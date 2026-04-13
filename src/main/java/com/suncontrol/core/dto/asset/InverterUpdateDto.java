package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.dto.component.InverterBaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
public class InverterUpdateDto extends InverterBaseDto {
    private DeviceStatus status;
    private BigDecimal lastAccumEnergy;
    private BigDecimal currentPower;

    public int getStatusCode() {
        return status.getCode();
    }
}
