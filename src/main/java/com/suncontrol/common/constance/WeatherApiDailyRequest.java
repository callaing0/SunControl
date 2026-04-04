package com.suncontrol.common.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum WeatherApiDailyRequest {
    TEMP_MAX("temperature_2m_max"),
    TEMP_MIN("temperature_2m_min"),
    WMO("weather_code"),
    SUNRISE("sunrise"),
    SUNSET("sunset"),
    SUNSHINE_DURATION("sunshine_duration"),
    DAYLIGHT_DURATION("daylight_duration"),
    PREC_SUM("precipitation_sum"),
    SNOW_SUM("snowfall_sum"),
    RADIATION_SUM("shortwave_radiation_sum");

    private final String label;

    public static final String DAILY_REQUEST;

    /// 일일 기상 데이터 요청자료 문자열 생성
    static {
        DAILY_REQUEST = Arrays.stream(WeatherApiDailyRequest.values())
                .map(WeatherApiDailyRequest::getLabel)
                .collect(Collectors.joining(","));
    }
}
