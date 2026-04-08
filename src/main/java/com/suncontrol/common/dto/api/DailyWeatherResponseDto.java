package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.dto.log.DailyWeatherDto;
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
public class DailyWeatherResponseDto {
    /// 기상 API 수신용 일별 데이터

    @JsonProperty("time")
    private List<LocalDate> baseDate;
    @JsonProperty("temperature_2m_max")
    private List<Double> tempMax;
    @JsonProperty("temperature_2m_min")
    private List<Double> tempMin;

    @JsonProperty("weather_code")
    private List<Integer> weatherCode;

    @JsonProperty("sunrise")
    private List<LocalDateTime> sunrise;
    @JsonProperty("sunset")
    private List<LocalDateTime> sunset;
    @JsonProperty("sunshine_duration")
    private List<Double> sunshineDuration;
    @JsonProperty("daylight_duration")
    private List<Double> daylightDuration;
    @JsonProperty("precipitation_sum")
    private List<Double> precSum;
    @JsonProperty("snowfall_sum")
    private List<Double> snowSum;
    @JsonProperty("shortwave_radiation_sum")
    private List<Double> radiationSum;
}
