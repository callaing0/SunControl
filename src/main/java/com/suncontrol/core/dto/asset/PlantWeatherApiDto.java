package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.common.District;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantWeatherApiDto {
    // todo : 기상조회용 발전소 정보
    private Long id;
    private District district;
    private int azimuth;
    private int tilt;
}
