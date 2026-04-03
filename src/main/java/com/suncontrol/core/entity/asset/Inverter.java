package com.suncontrol.core.entity.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.asset.InverterType;
import lombok.AccessLevel;
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

    @Setter(AccessLevel.NONE)
    private BigDecimal efficiency;

    private BigDecimal measuredCapacity;

    private BigDecimal currentPower;
    private BigDecimal lastAccumEnergy;
    private InverterType inverterType;
    private DeviceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

/**
 * Mybatis Mapper 연동 및 DB 저장용 가상 필드
 */
    public void setType(String label) {
        this.inverterType = InverterType.fromLabel(label);
    }

    public void setEfficiency(BigDecimal efficiency) {
        if(this.efficiency != null) {
            this.efficiency = efficiency;
            return;
        }
        this.efficiency = this.inverterType.getEfficiency();
    }

    public String getType() {
        return this.inverterType != null ?
                this.inverterType.getLabel() : null;
    }


    public void setStatusCode(int statusCode) {
        this.status = DeviceStatus.fromCode(statusCode);
    }

    public Integer getStatusCode() {
        return this.status != null ?
                this.status.getCode() : null;
    }
}
