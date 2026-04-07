package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StationPlantDto {

    private Long plantId;              // 발전소 식별자
    private String name;               // 발전소 이름
    private String ownerName;          // 담당자 이름
    private String address;            // 지역 주소
}
