package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
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
    /// List<DailyWeatherDto> 를 통해 DB저장,
    /// Map<지역, Map<날짜, DailyWeatherDto>> 로
    /// 발전 데이터 생성용 맵 객체를 형성한다

    private District district;
    private LocalDate baseDate;
    private ReportDataType dataType;

    private double tempMax;
    private double tempMin;
    private Integer weatherCode;
    private Weather weather;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private double sunshineDuration;
    private double daylightDuration;
    private double precSum;
    private double snowSum;
    private double radiationSum;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DailyWeatherDto(DailyWeather entity) {
        this.district = entity.getDistrict();
        this.baseDate = entity.getBaseDate();
        this.tempMax = entity.getTempMax();
        this.tempMin = entity.getTempMin();
        this.weatherCode = entity.getWeatherCode();
        this.weather = Weather.fromCode(weatherCode);
        this.sunrise = entity.getSunrise();
        this.sunset = entity.getSunset();
        this.sunshineDuration = entity.getSunshineDuration();
        this.daylightDuration = entity.getDaylightDuration();
        this.precSum = entity.getPrecSum();
        this.snowSum = entity.getSnowSum();
        this.radiationSum = entity.getRadiationSum();
        this.dataType = ReportDataType.findByDayOffset(entity.getDayOffset());
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public Integer getDayOffset() {
        return dataType != null ? this.dataType.getDayOffset() : ReportDataType.UNKNOWN.getDayOffset();
    }
}
