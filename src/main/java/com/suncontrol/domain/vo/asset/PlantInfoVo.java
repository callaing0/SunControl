package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.PlantDto;
import com.suncontrol.core.entity.view.PlantInfoView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class PlantInfoVo extends PlantVo {
    /// 내비게이션 메뉴용,
    private District district;
    private BigDecimal totalValue;
    private DeviceStatus status;

    public PlantInfoVo(PlantInfoView view) {
        super(view == null ? null : new PlantDto(view.getPlant()));
        if(view == null) return;
        this.district = view.getDistrict();
        this.totalValue = view.getTotalValue();
    }
}
