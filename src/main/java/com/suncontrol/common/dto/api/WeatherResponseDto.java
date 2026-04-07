package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
public class WeatherResponseDto {
    /// 기상 API 수신용 Dto

    @JsonProperty("latitude")
    private BigDecimal latitude;
    @JsonProperty("longitude")
    private BigDecimal longitude;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("hourly")
    private List<WeatherHourlyResponseDto> hourly;
    @JsonProperty("daily")
    private List<DailyWeatherResponseDto> daily;
    @JsonIgnore
    private PlantWeatherApiDto plant; /// API 수신 후 발전소 정보 외부에서 주입

    @JsonIgnore
    public List<WeatherLogDto> getWeatherLogs() {
        /// WeatherHourlyResponseDto의 getWeatherLogDto() 를 이용해
        /// 리스트로 변환하여 반환
        if(this.hourly == null) return Collections.emptyList();
        return hourly.stream()
                .map(h -> h
                        .getWeatherLogDto(this.plant.getDistrict())
                )
                .toList();
    }

    @JsonIgnore
    public List<RadiationLogDto> getRadiationLogs() {
        /// WeatherHourlyResponseDto의 getRadiationLogDto() 를 이용해
        /// 리스트로 변환하여 반환
        if(this.hourly == null) return Collections.emptyList();
        return hourly.stream()
                .map(h -> h
                        .getRadiationLogDto(this.plant.getId())
                )
                .toList();
    }

    @JsonIgnore
    public List<DailyWeatherDto> getDailyWeathers() {
        if(this.daily == null) return Collections.emptyList();
        return daily.stream()
                .map(d -> d
                        .getDailyWeatherDto(this.plant.getDistrict())
                )
                .toList();
    }
}
