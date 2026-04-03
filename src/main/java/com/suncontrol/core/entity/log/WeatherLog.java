package com.suncontrol.core.entity.log;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherLog {
    private Long id;
    private String districtCode;
    private LocalDateTime baseTime;
    private double temperature;
    private int cloudLow;
    private int cloudMid;
    private int cloudHigh;
    private int ghi;
    private String weatherCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
