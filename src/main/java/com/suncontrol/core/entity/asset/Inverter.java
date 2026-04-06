package com.suncontrol.core.entity.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.asset.InverterType;
import com.suncontrol.core.dto.asset.InverterDataTransferObject;
import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inverter {
    private Long id;
    private Long plantId;
    private String serial;
    private BigDecimal ratedCapacity;
    private BigDecimal efficiency;

    private BigDecimal measuredCapacity;

    private BigDecimal currentPower;
    private BigDecimal lastAccumEnergy;
    private InverterType inverterType;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int statusCode;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private DeviceStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Inverter(InverterDataTransferObject dto) {
        this.plantId = dto.getPlantId();
        this.ratedCapacity = dto.getRatedCapacity();
        /// 가상 필드용 메서드 활용
        this.inverterType = dto.getInverterType();
        this.efficiency = dto.getEfficiency();
        this.serial = dto.getSerial();
    }

    public void setCapSurplus(InverterCapSurplusDto dto) {
        this.measuredCapacity = this.measuredCapacity.add(dto.getMeasuredCapSurplus());
    }

/**
 * Mybatis Mapper 연동 및 DB 저장용 가상 필드
 */
    public void setType(String label) {
        this.inverterType = InverterType.fromLabel(label);
    }

    public void setEfficiency(BigDecimal efficiency) {
        if(efficiency != null) {
            this.efficiency = efficiency;
        } else if (this.inverterType != null) {
            this.efficiency = this.inverterType.getEfficiency();
        } else {
            this.efficiency = BigDecimal.valueOf(100);
        }
    }

    public String getType() {
        return this.inverterType != null ?
                this.inverterType.getLabel() : null;
    }


    public void setStatusCode(Integer statusCode) {
        this.status = DeviceStatus.fromCode(statusCode);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return this.status != null ?
                this.status.getCode() : this.statusCode;
    }

    public void setStatus(Integer statusCode) {
        this.status = DeviceStatus.fromCode(statusCode);
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public DeviceStatus getStatus() {
        return this.status != null ?
                this.status : DeviceStatus.fromCode(statusCode);
    }
}
