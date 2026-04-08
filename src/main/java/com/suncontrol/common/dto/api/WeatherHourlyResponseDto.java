package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class WeatherHourlyResponseDto {
    ///  기상 API 수신 시간별 데이터

    @JsonProperty("time")
    private List<LocalDateTime> baseTime;

    @JsonProperty("temperature_2m")
    private List<Double> temperature;

    @JsonProperty("cloud_cover_low")
    private List<Integer> cloudLow;
    @JsonProperty("cloud_cover_mid")
    private List<Integer> cloudMid;
    @JsonProperty("cloud_cover_high")
    private List<Integer> cloudHigh;

    @JsonProperty("shortwave_radiation")
    private List<Integer> ghi;
    @JsonProperty("weather_code")
    private List<Integer> weatherCode;

    @JsonProperty("global_tilted_irradiance")
    private List<Double> gti;
    @JsonProperty("global_tilted_irradiance_instant")
    private List<Double> gtiInstant;
}
