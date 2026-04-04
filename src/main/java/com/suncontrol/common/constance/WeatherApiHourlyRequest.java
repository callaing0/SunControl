package com.suncontrol.common.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum WeatherApiHourlyRequest {
    TEMPERATURE("temperature_2m"),
    CLOUD_LOW("cloud_cover_low"),
    CLOUD_MID("cloud_cover_mid"),
    CLOUD_HIGH("cloud_cover_high"),
    GHI("shortwave_radiation"),
    WMO("weather_code"),
    GTI("global_tilted_irradiance"),
    GTI_INSTANCE("global_tilted_irradiance_instance");

    private final String label;

    public static final String HOURLY_REQUEST;

    /// 시간당 데이터 요청자료 문자열 생성
    static {
        HOURLY_REQUEST = Arrays.stream(WeatherApiHourlyRequest.values())
                .map(WeatherApiHourlyRequest::getLabel)
                .collect(Collectors.joining(","));
    }
}
