package com.suncontrol.core.entity.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WeatherLog {
    private Long id;
    private String districtCode;
    private LocalDateTime baseTime;
    private double temperature;
    private int cloudLow;
    private int cloudMid;
    private int cloudHigh;
    private int ghi;

    private Integer weatherCode;

    private Integer dayOffset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public District getDistrict() {
        return District.fromCode(districtCode);
    }

    public WeatherLog(WeatherLogDto dto) {
        this.districtCode = dto.getDistrict().getCode();
        this.baseTime = dto.getBaseTime();
        this.temperature = dto.getTemperature();
        this.cloudLow = dto.getCloudLow();
        this.cloudMid = dto.getCloudMid();
        this.cloudHigh = dto.getCloudHigh();
        this.ghi = dto.getGhi();
        this.weatherCode = dto.getWeatherCode();
        this.dayOffset = dto.getDayOffset();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}
