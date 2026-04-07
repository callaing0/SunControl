package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DailyWeatherResponseDto {
    /// 기상 API 수신용 일별 데이터

    @JsonProperty("time")
    @Getter(AccessLevel.NONE)
    private LocalDateTime baseDate;
    @JsonProperty("temperature_2m_max")
    private double tempMax;
    @JsonProperty("temperature_2m_min")
    private double tempMin;

    @JsonProperty("weather_code")
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    /// Weather Enum 강제 매핑을 위해 Lombok 기본 메서드 생성 제한
    private String weatherCode;

    @JsonProperty("sunrise")
    private LocalDateTime sunrise;
    @JsonProperty("sunset")
    private LocalDateTime sunset;
    @JsonProperty("sunshine_duration")
    private double sunshineDuration;
    @JsonProperty("daylight_duration")
    private double daylightDuration;
    @JsonProperty("precipitation_sum")
    private double precSum;
    @JsonProperty("snowfall_sum")
    private double snowSum;
    @JsonProperty("shortwave_radiation_sum")
    private double radiationSum;


    @JsonIgnore
    @Getter(AccessLevel.NONE)
    /// 기상 API조회시 나오는 두자리 숫자 문자열로 "기상코드" 객체 찾아서 저장
    private Weather weather;

    /// 커스텀 setter/getter로 Weather 객체 형성
    public void setWeatherCode(String weatherCode) {
        this.weather = Weather.fromCode(weatherCode);
        this.weatherCode = weatherCode;
    }
    public String getWeatherCode() {
        return weatherCode == null ?
                weather == null ? null : weather.getWeatherCode() : this.weatherCode;
    }

    /// DB 저장할 때 LocalDateTime 에서 반환하여 저장하기 위한 getter메서드
    public LocalDate getBaseDate() {
        return this.baseDate == null ? null : this.baseDate.toLocalDate();
    }

    @JsonIgnore
    public Weather getWeather() {
        /// 마지막 Entity로 옮길 때 데이터 일치여부를 확인하고
        /// DB로 전송하기 위한 커스텀 getter 메서드
        Weather weatherByCode = Weather.fromCode(weatherCode);
        /// weather Enum 객체에 제대로 값이 저장되지 않았을 경우 재추적하여 갱신
        if(!weatherByCode.equals(weather)) {
            this.weather = weatherByCode;

            /// 외부에서 입력된 값이 없으면 강제로 "맑음" 주입
            if(this.weatherCode == null)
                this.weatherCode = weather.getWeatherCode();

        }
        return this.weather;
    }

    @JsonIgnore
    public Integer getDayOffset() {
        return this.getBaseDate().compareTo(LocalDate.now());
    }
    /// Entity 송신용 DailyWeatherDto "간이 생성자"
    @JsonIgnore
    public DailyWeatherDto getDailyWeatherDto(District district) {
        DailyWeatherDto dto = new DailyWeatherDto();
        dto.setDistrict(district);
        dto.setBaseDate(getBaseDate());
        dto.setTempMax(this.tempMax);
        dto.setTempMin(this.tempMin);
        dto.setWeatherCode(this.weatherCode);
        dto.setWeather(getWeather()); /// 데이터 정합성 검증
        dto.setSunrise(this.sunrise);
        dto.setSunset(this.sunset);
        dto.setSunshineDuration(this.sunshineDuration);
        dto.setDaylightDuration(this.daylightDuration);
        dto.setPrecSum(this.precSum);
        dto.setSnowSum(this.snowSum);
        dto.setRadiationSum(this.radiationSum);
        dto.setDataType(ReportDataType.findByDayOffset(getDayOffset()));

        return dto;
    }
}
