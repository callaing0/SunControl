package com.suncontrol.common.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum WeatherApiDailyRequest {
    TEMP_MAX("temperature_2m_max"), /// 최고기온
    TEMP_MIN("temperature_2m_min"), /// 최저기온
    WMO("weather_code"), /// 기상코드
    SUNRISE("sunrise"), /// 일출시각
    SUNSET("sunset"); /// 일몰시각
    //SUNSHINE_DURATION("sunshine_duration"), /// 일조시간(해가 떠 있는 시간)
    //DAYLIGHT_DURATION("daylight_duration"), /// 일사시간(햇빛이 지면에 도달했던 시간)
    //PREC_SUM("precipitation_sum"), /// 강수량(통계분석용)
    //SNOW_SUM("snowfall_sum"), /// 적설량(통계분석용)
    //RADIATION_SUM("shortwave_radiation_sum"); /// 일일 일사량 합계

    private final String label;

    public static final String DAILY_REQUEST;

    /// 일일 기상 데이터 요청자료 문자열 생성
    static {
        DAILY_REQUEST = Arrays.stream(WeatherApiDailyRequest.values())
                .map(WeatherApiDailyRequest::getLabel)
                .collect(Collectors.joining(","));
    }
}
