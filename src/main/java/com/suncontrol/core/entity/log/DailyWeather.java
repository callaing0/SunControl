package com.suncontrol.core.entity.log;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DailyWeather {
    private Long id;
    private String districtCode;
    private LocalDate baseDate;
    private double tempMax;
    private double tempMin;
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
}
