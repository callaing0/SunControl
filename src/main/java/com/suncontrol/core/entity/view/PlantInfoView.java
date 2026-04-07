package com.suncontrol.core.entity.view;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Province;
import com.suncontrol.core.entity.asset.Plant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PlantInfoView {
    /// Plant, Inverter 테이블을 참조한
    /// 조회용 뷰 객체

    private Plant plant = new Plant();

    private BigDecimal capacitySum;
    /// 소속 인버터 용량합계
    /// min(ratedCapacity, measuredCapacity)

    private BigDecimal totalValue;
    /// 소속 인버터 출력합계

    private BigDecimal accumTotal;
    /// 소속 인버터 누적발전량 합계

    private BigDecimal efficiency;
    /// 출력합계 / 용량합계 * 100

    private int statusCode;
    private LocalDateTime updatedAt;

    /// 객체 변환을 위한 가상필드
    public void setId(Long id) {
        this.plant.setId(id);
    }
    public void setName(String name) {
        this.plant.setName(name);
    }
    public void setAddress(String address) {
        this.plant.setAddress(address);
    }
    public void setLatitude(BigDecimal latitude) {
        this.plant.setLatitude(latitude);
    }
    public void setLongitude(BigDecimal longitude) {
        this.plant.setLongitude(longitude);
    }
    public void setProvinceCode(String provinceCode) {
        this.plant.setProvinceCode(provinceCode);
    }
    public void setProvince(Province province) {
        this.plant.setProvince(province);
    }
    public void setDistrictCode(String districtCode) {
        this.plant.setDistrictCode(districtCode);
    }
    public void setDistrict(District district) {
        this.plant.setDistrict(district);
    }
    public void setMain(Boolean isMain) {
        this.plant.setMain(isMain);
    }
    public void setAzimuth(int azimuth) {
        this.plant.setAzimuth(azimuth);
    }
    public void setTilt(int tilt) {
        this.plant.setTilt(tilt);
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.plant.setCreatedAt(createdAt);
    }
    public Long  getId() {
        return this.plant.getId();
    }
    public String getName() {
        return this.plant.getName();
    }
    public String getAddress() {
        return this.plant.getAddress();
    }
    public BigDecimal getLatitude() {
        return this.plant.getLatitude();
    }
    public BigDecimal getLongitude() {
        return this.plant.getLongitude();
    }
    public String getProvinceCode() {
        return this.plant.getProvinceCode();
    }
    public  Province getProvince() {
        return this.plant.getProvince();
    }
    public String getDistrictCode() {
        return this.plant.getDistrictCode();
    }
    public District getDistrict() {
        return this.plant.getDistrict();
    }
    public boolean isMain() {
        return this.plant.isMain();
    }
    public int  getAzimuth() {
        return this.plant.getAzimuth();
    }
    public int  getTilt() {
        return this.plant.getTilt();
    }
    public LocalDateTime getCreatedAt() {
        return this.plant.getCreatedAt();
    }
}
