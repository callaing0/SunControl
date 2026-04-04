package com.suncontrol.core.entity.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DailyWeather {
    private Long id;
    private District district;
    private LocalDate baseDate;
    private double tempMax;
    private double tempMin;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Weather weather;
    private String weatherCode;

    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private double suhshineDuration;
    private double daylightDuration;
    private double precSum;
    private double snowSum;
    private double radiationSum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /// DB 저장 조회용 가상필드
    public void setWeatherCode(String weatherCode) {
        this.weather = Weather.fromCode(weatherCode);
        this.weatherCode = weatherCode;
    }
    public String getWeatherCode() {
        return weatherCode == null ?
                weather == null ? null : weather.getWeatherCode() : this.weatherCode;
    }
    public void setDistrictCode(String districtCode) {
        this.district = District.fromCode(districtCode);
    }
    public String getDistrictCode() {
        return this.district != null ?
                district.getCode() : null;
    }
}
