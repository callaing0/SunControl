package com.suncontrol.core.dto.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyWeatherDto {
    /// 기상 API 수신용 및 발전 데이터 생성용 일별 데이터
    /// Map<DistrictCode, List<DailyWeatherDto>> 를 통해 DB저장,
    /// Map<DistrictCode, Map<LocalDate, DailyWeatherDto>>
    /// 발전 데이터 생성용 맵 객체
    // todo
}
