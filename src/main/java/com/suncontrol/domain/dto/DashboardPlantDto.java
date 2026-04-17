package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardPlantDto {
    private Long plantId;
    private String plantName;
    private String location;
    private String districtCode;
}