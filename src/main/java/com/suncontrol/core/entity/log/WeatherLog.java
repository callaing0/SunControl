package com.suncontrol.core.entity.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class WeatherLog {
    private Long id;
    private District district;
    private LocalDateTime baseTime;
    private double temperature;
    private int cloudLow;
    private int cloudMid;
    private int cloudHigh;
    private int ghi;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Weather weather;
    private String weatherCode;

    private Integer dayOffset;
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

    public WeatherLog(WeatherLogDto dto) {
        this.district = dto.getDistrict();
        this.baseTime = dto.getBaseTime();
        this.temperature = dto.getTemperature();
        this.cloudLow = dto.getCloudLow();
        this.cloudMid = dto.getCloudMid();
        this.cloudHigh = dto.getCloudHigh();
        this.ghi = dto.getGhi();
        this.dayOffset = dto.getDayOffset();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}
