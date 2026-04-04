package com.suncontrol.common.dto;

import com.suncontrol.core.constant.util.GenerationStrategy;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WeatherCalcDto {
    private double gti;
    private double temperature;
    private GenerationStrategy expStrategy = GenerationStrategy.VIRTUAL_EXP;
    private double sunriseHr = 6.0;
    private double sunsetHr = 19.0;

    public WeatherCalcDto(WeatherLogDto weather,
                          RadiationLogDto radiation,
                          DailyWeatherDto daily) {
        if(weather == null || radiation == null || daily == null) {
            return;
        }
        this.gti = radiation.getGti();
        this.temperature = weather.getTemperature();
        this.expStrategy = GenerationStrategy.REAL_EXP;
        LocalDateTime sunrise = daily.getSunrise();
        this.sunriseHr = sunrise.getHour() +
                (sunrise.getMinute() / 60.0) +
                (sunrise.getSecond() / 3600.0);
        LocalDateTime sunset = daily.getSunset();
        this.sunsetHr = sunset.getHour() +
                (sunset.getMinute() / 60.0) +
                (sunset.getSecond() / 3600.0);
    }
}
