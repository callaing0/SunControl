package com.suncontrol.common.dto;

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
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String timezone;
    private int tilt;
    private int azimuth;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private String hourly;
    private String daily;
    @JsonIgnore
    private District district;
    @JsonIgnore
    private Long plantId;

    public WeatherRequestDto(PlantWeatherApiDto dto, LocalDate start, LocalDate end) {
        this.latitude = dto.getDistrict().getLatitude();
        this.longitude = dto.getDistrict().getLongitude();
        this.timezone = dto.getDistrict().getTimezone();
        this.tilt = dto.getTilt();
        this.azimuth = dto.getAzimuth();
        this.startDate = start;
        this.endDate = end;
        this.district = dto.getDistrict();
        this.plantId = dto.getId();
        this.hourly = WeatherApiHourlyRequest.HOURLY_REQUEST; /// 시간 데이터 요청
        this.daily = WeatherApiDailyRequest.DAILY_REQUEST; /// 일간 데이터 요청
    }
}
