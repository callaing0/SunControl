package com.suncontrol.core.dto.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherLogDto {

    /// Map<DistrictCode,List<WeatherLogDto>>를 이용하여 DB저장
    /// Map<D.Code,Map<L.D.T,WeatherLogDto>>를 이용하여 발전데이터 생성
    //todo
}
