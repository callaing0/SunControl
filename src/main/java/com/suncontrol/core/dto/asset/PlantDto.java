package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Province;
import com.suncontrol.core.entity.asset.Plant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PlantDto {
    /// PlantService 파라미터, 반환 전용 DTO
    /// Form -> (Dto -> Entity -> Dto) -> Dto, Vo 형식으로 구성하고
    /// core 패키지의 데이터 객체를 줄이기 위함
    private Long memberId;
    private Long id;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Province province;
    private District district;
    private boolean isMain;
    private int azimuth;
    private int tilt;
    private LocalDateTime createdAt;

    /// 발전소 DB 조회용 생성자
    public PlantDto(Plant entity) {
        this.memberId = entity.getMemberId();
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.province = entity.getProvince();
        this.district = entity.getDistrict();
        this.azimuth = entity.getAzimuth();
        this.tilt = entity.getTilt();
        this.createdAt = entity.getCreatedAt();
    }
}
