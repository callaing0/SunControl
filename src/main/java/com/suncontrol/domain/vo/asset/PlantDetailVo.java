package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.constant.common.Province;
import com.suncontrol.core.entity.view.PlantInfoView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class PlantDetailVo extends PlantInfoVo {
    /// 마이페이지용
    private String address; // 주소
    private BigDecimal latitude; // 위도
    private BigDecimal longitude; // 경도
    private Province province; // 지역
    private int azimuth; // 방위각
    private int tilt; // 경사각

    public PlantDetailVo(PlantInfoView view) {
        super(view);
        if(view == null) return;
        this.address = view.getAddress();
        this.latitude = view.getLatitude();
        this.longitude = view.getLongitude();
        this.province = view.getProvince();
        this.azimuth = view.getAzimuth();
        this.tilt = view.getTilt();
    }

    public PlantVo toPlantVo() {
        return new PlantVo(this);
    }

    public String getName() {
        return super.getName();
    }
}
