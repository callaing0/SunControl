package com.suncontrol.core.entity.log;

import com.suncontrol.core.constant.common.Weather;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GenerationLog {
    private Long id;
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Weather weather;
    private String weatherCode;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /// DB 저장 조회용 가상필드
    public void setWeatherCode(String weatherCode) {
        this.weather = Weather.fromCode(weatherCode);
        this.weatherCode = weatherCode;
    }
    public String getWeatherCode() { /// DB에 null이 저장되었을 경우를 대비한 방어로직
        return weatherCode == null ?
                weather == null ? null : weather.getWeatherCode() : this.weatherCode;
    }
}
