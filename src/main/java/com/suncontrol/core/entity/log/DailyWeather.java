package com.suncontrol.core.entity.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DailyWeather {
    private Long id;
    private String districtCode;
    private LocalDate baseDate;
    private double tempMax;
    private double tempMin;

    private Integer weatherCode;

    private LocalDateTime sunrise;
    private LocalDateTime sunset;

    private double sunshineDuration;
    private double daylightDuration;
    private double precSum;
    private double snowSum;
    private double radiationSum;
    private Integer dayOffset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /// DB 저장 조회용 가상필드
    public District getDistrict() {
        return District.fromCode(districtCode);
    }

    public DailyWeather(DailyWeatherDto dto) {
        this.districtCode = dto.getDistrict().getCode();
        this.baseDate = dto.getBaseDate();
        this.tempMax = dto.getTempMax();
        this.tempMin = dto.getTempMin();
        this.weatherCode = dto.getWeatherCode();
        this.sunrise = dto.getSunrise();
        this.sunset = dto.getSunset();
        this.sunshineDuration = dto.getSunshineDuration();
        this.daylightDuration = dto.getDaylightDuration();
        this.precSum = dto.getPrecSum();
        this.snowSum = dto.getSnowSum();
        this.radiationSum = dto.getRadiationSum();
        this.dayOffset = dto.getDayOffset();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}
