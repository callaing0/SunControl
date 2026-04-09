package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.dto.asset.InverterDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InverterDetailVo extends InverterInfoVo {
    /// 인버터 정보용(마이페이지)
    private BigDecimal ratedCapacity;
    private BigDecimal measuredCapacity;
    private BigDecimal lastAccumEnergy;

    public InverterDetailVo(PlantVo plant,InverterDto dto) {
        super(plant, dto);
        if(plant == null || dto == null) return;
        this.ratedCapacity = dto.getRatedCapacity();
        this.measuredCapacity = dto.getMeasuredCapacity();
        this.lastAccumEnergy = dto.getLastAccumEnergy();
    }
}
