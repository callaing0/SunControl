package com.suncontrol.common.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum WeatherApiHourlyRequest {
    TEMPERATURE("temperature_2m"); /// 기온
    //CLOUD_LOW("cloud_cover_low"), /// 저고도 구름 양
    //CLOUD_MID("cloud_cover_mid"), /// 중고도 구름 양
    //CLOUD_HIGH("cloud_cover_high"), /// 고고도 구름 양
//    GHI("shortwave_radiation"), /// 수직면 일사량(위도때문에 GTI보다 낮음)
//    WMO("weather_code"), /// 기상코드
//    GTI("global_tilted_irradiance"), /// 경사면 일사량
//    GTI_INSTANCE("global_tilted_irradiance_instance"); /// 순간 경사면 일사량

    private final String label;

    public static final String HOURLY_REQUEST;

    /// 시간당 데이터 요청자료 문자열 생성
    static {
        HOURLY_REQUEST = Arrays.stream(WeatherApiHourlyRequest.values())
                .map(WeatherApiHourlyRequest::getLabel)
                .collect(Collectors.joining(","));
    }
}
