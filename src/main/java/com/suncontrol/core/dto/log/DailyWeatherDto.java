package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.entity.log.DailyWeather;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DailyWeatherDto {
    /// 발전 데이터 "통계" 생성용 일별 데이터
    /// Map<District, List<DailyWeatherDto>> 를 통해 DB저장,
    /// Map<District, Map<LocalDate, DailyWeatherDto>> 로
    /// 발전 데이터 생성용 맵 객체를 형성한다

    private LocalDate baseDate;
    private double tempMax;
    private double tempMin;

    @Getter(AccessLevel.NONE)
    private String weatherCode;
    private Weather weather;

    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private double sunshineDuration;
    private double daylightDuration;
    private double precSum;
    private double snowSum;
    private double radiationSum;


    public String getWeatherCode() {
        return weatherCode == null ?
                weather == null ? null : weather.getWeatherCode() : this.weatherCode;
    }

    public DailyWeatherDto(DailyWeather entity) {
        this.baseDate = entity.getBaseDate();
        this.tempMax = entity.getTempMax();
        this.tempMin = entity.getTempMin();
        this.weather = entity.getWeather();
        this.weatherCode = entity.getWeatherCode();
        this.sunrise = entity.getSunrise();
        this.sunset = entity.getSunset();
        this.sunshineDuration = entity.getSunshineDuration();
        this.daylightDuration = entity.getDaylightDuration();
        this.precSum = entity.getPrecSum();
        this.snowSum = entity.getSnowSum();
        this.radiationSum = entity.getRadiationSum();
    }
}
