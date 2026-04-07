package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeatherResponseDto {
    /// 기상 API 수신용 Dto

    @JsonProperty("latitude")
    private BigDecimal latitude;
    @JsonProperty("longitude")
    private BigDecimal longitude;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("hourly") //TODO 객체의 리스트가 아니라 리스트를 필드로 가진 객체임
    private List<WeatherHourlyResponseDto> hourly;
    @JsonProperty("daily")
    private List<DailyWeatherResponseDto> daily;
    @JsonIgnore
    private PlantWeatherApiDto plant; /// API 수신 후 발전소 정보 외부에서 주입

    @JsonIgnore
    private final LocalDateTime responseTime = LocalDateTime.now();
    /// 각 날씨 컬럼의 created_at이나 updated_at을 강제로 이것으로 주입하기 위함

    @JsonIgnore
    public List<WeatherLogDto> getWeatherLogs() {
        /// WeatherHourlyResponseDto의 getWeatherLogDto() 를 이용해
        /// 리스트로 변환하여 반환
        if(this.hourly == null) return Collections.emptyList();
        return hourly.stream()
                .map(h -> h
                        .getWeatherLogDto(this.plant.getDistrict(), responseTime)
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
                        .getRadiationLogDto(this.plant.getId(), responseTime)
                )
                .toList();
    }

    @JsonIgnore
    public List<DailyWeatherDto> getDailyWeathers() {
        if(this.daily == null) return Collections.emptyList();
        return daily.stream()
                .map(d -> d
                        .getDailyWeatherDto(this.plant.getDistrict(), responseTime)
                )
                .toList();
    }
}
