package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherHourlyResponseDto {
    ///  기상 API 수신 시간별 데이터

    @JsonProperty("time")
    private LocalDateTime baseTime;

    @JsonProperty("temperature_2m")
    private double temperature;

    @JsonProperty("cloud_cover_low")
    private int cloudLow;
    @JsonProperty("cloud_cover_mid")
    private int cloudMid;
    @JsonProperty("cloud_cover_high")
    private int cloudHigh;

    @JsonProperty("shortwave_radiation")
    private int ghi;
    @JsonProperty("weather_code")
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String weatherCode;
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private Weather weather;

    @JsonProperty("global_tilted_irradiance")
    private double gti;
    @JsonProperty("global_tilted_irradiance_instance")
    private double gtiInstance;

    public void setWeatherCode(String weatherCode) {
        this.weather = Weather.fromCode(weatherCode);
        this.weatherCode = weatherCode;
    }
    public String getWeatherCode() {
        return weatherCode == null ?
                weather == null ? null : weather.getWeatherCode() : this.weatherCode;
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
    public WeatherLogDto getWeatherLogDto() {
        return WeatherLogDto.builder()
                .baseTime(baseTime)
                .temperature(temperature)
                .cloudLow(cloudLow)
                .cloudMid(cloudMid)
                .cloudHigh(cloudHigh)
                .ghi(ghi)
                .weather(getWeather())
                .weatherCode(weatherCode)
                .build();
    }

    @JsonIgnore
    public RadiationLogDto getRadiationLogDto() {
        return RadiationLogDto.builder()
                .baseTime(baseTime)
                .gti(gti)
                .gtiInstance(gtiInstance)
                .build();
    }
}
