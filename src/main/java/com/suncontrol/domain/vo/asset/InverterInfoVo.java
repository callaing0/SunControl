package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.dto.asset.InverterDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class InverterInfoVo {
    /// 인버터 정보용(대시보드)
    private PlantVo plant;
    private Long id;
    private String serial;
    private DeviceStatus status;
    private BigDecimal currentPower;
    private BigDecimal capacity;
    private BigDecimal efficiency;

    public InverterInfoVo(PlantVo plant, InverterDto dto) {
        if(plant == null || dto == null) return;
        this.plant = plant;
        this.id = dto.getId();
        this.serial = dto.getSerial();
        this.status = dto.getStatus();
        this.currentPower = dto.getCurrentPower();
        this.capacity = dto.getRatedCapacity().min(dto.getMeasuredCapacity());
        this.efficiency = dto.getEfficiency();
    }

    public Long getPlantId() {
        return plant.getId();
    }

    public BigDecimal getPerformanceRate() {
        return currentPower.divide(capacity, 3, RoundingMode.HALF_EVEN);
    }

    public String getName() {
        return serial;
    }
}
