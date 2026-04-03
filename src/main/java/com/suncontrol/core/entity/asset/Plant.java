package com.suncontrol.core.entity.asset;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Province;
import com.suncontrol.core.dto.asset.form.PlantSaveForm;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Plant {
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Province province;
    private District district;
    private boolean isMain;
    private boolean isDeleted;
    private int azimuth;
    private int tilt;
    private LocalDateTime createdAt;

    /// 발전소 DB 저장용 생성자
    public Plant(PlantSaveForm form) {
        this.memberId = form.getMemberId();
        this.name = form.getName();
        this.address = form.getAddress();
        this.latitude = form.getLatitude();
        this.longitude = form.getLongitude();
        this.district = form.getDistrict();
        this.azimuth = form.getAzimuth();
        this.tilt = form.getTilt();
    }

    /**
    *  MyBatis mapper 연동 및 DB 저장용 가상 필드
    *  DB(String, 5자리 또는 2자리) <->
    *      객체(District / Province Enum)
    */
    public String getProvinceCode() {
        return this.province != null ?
                province.getCode() : null;
    }

    public void setProvinceCode(String provinceCode) {
        this.province =  Province.fromCode(provinceCode);
    }

    public String getDistrictCode() {
        return this.district != null ?
                district.getCode() : null;
    }

    public void  setDistrictCode(String districtCode) {
        this.district = District.fromCode(districtCode);
    }
}
