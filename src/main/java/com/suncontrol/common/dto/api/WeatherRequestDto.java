package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suncontrol.common.constance.WeatherApiDailyRequest;
import com.suncontrol.common.constance.WeatherApiHourlyRequest;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class WeatherRequestDto {
    /// 기상 API송신용 DTO
    @JsonProperty("past_days")
    private int pastDays;
    @JsonProperty("forecast_days")
    private int forecastDays;

    @JsonProperty("hourly")
    private String hourly;
    @JsonProperty("daily")
    private String daily;
    @JsonIgnore
    private PlantWeatherApiDto plant;

    public WeatherRequestDto(PlantWeatherApiDto dto, int pastDays, int forecastDays) {
        if (dto == null) throw new IllegalArgumentException("발전소 정보없음");
        this.pastDays = pastDays;
        this.forecastDays = forecastDays;
        this.plant = dto;
        this.hourly = WeatherApiHourlyRequest.HOURLY_REQUEST; /// 시간 데이터 요청
        this.daily = WeatherApiDailyRequest.DAILY_REQUEST; /// 일간 데이터 요청
    }

    public WeatherRequestDto(PlantWeatherApiDto dto) {
        if (dto == null) throw new IllegalArgumentException("발전소 정보없음");
        this.pastDays = 0;
        this.forecastDays = 7;
        this.plant = dto;
        this.hourly = WeatherApiHourlyRequest.HOURLY_REQUEST; /// 시간 데이터 요청
        this.daily = WeatherApiDailyRequest.DAILY_REQUEST; /// 일간 데이터 요청
    }

    @JsonProperty("latitude")
    public BigDecimal getLatitude() {
        return plant.getDistrict().getLatitude();
    }

    @JsonProperty("longitude")
    public BigDecimal getLongitude() {
        return plant.getDistrict().getLongitude();
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return District.TIMEZONE;
    }

    @JsonProperty("tilt")
    public int getTilt() {
        return plant.getTilt();
    }

    @JsonProperty("azimuth")
    public int getAzimuth() {
        return plant.getAzimuth();
    }
}
