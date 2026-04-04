package com.suncontrol.common.dto;

import com.suncontrol.core.constant.util.GenerationStrategy;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;

import java.time.LocalDateTime;

/// 데이터 생성을 위해 필요한 날씨정보와 설비정보를 담는 읽기전용 객체
public record GenerateCalcBase(
        double gti,
        double gtiInstance,
        double temperature,
        GenerationStrategy expStrategy,
        double sunriseHr,
        double sunsetHr
        /// todo InverterGenerationDto에서 필요한 정보를 읽기
) {
    private static final double DEFAULT_ZERO = 0.0;
    private static final double DEFAULT_SUNRISE = 6.0;
    private static final double DEFAULT_SUNSET = 19.0;
    private static final double MINUTES = 60.0;
    private static final double SECONDS = 3600.0;

    public static GenerateCalcBase defaultValues() {
        return new GenerateCalcBase(DEFAULT_ZERO, DEFAULT_ZERO, DEFAULT_ZERO,
                GenerationStrategy.VIRTUAL_EXP, DEFAULT_SUNRISE, DEFAULT_SUNSET);
    }

    public GenerateCalcBase(WeatherLogDto weather,
                            RadiationLogDto radiation,
                            DailyWeatherDto daily) {
        this(
                radiation != null ? radiation.getGti() : DEFAULT_ZERO,
                radiation != null ? radiation.getGtiInstance() : DEFAULT_ZERO,
                weather != null ? weather.getTemperature() : DEFAULT_ZERO,
                weather != null && radiation != null && daily != null ?
                        GenerationStrategy.REAL_EXP : GenerationStrategy.VIRTUAL_EXP,
                daily != null && daily.getSunrise() != null ?
                        toHour(daily.getSunrise()) : DEFAULT_SUNRISE,
                daily != null && daily.getSunset() != null ?
                        toHour(daily.getSunset()) : DEFAULT_SUNSET
        );
    }

    private static double toHour(LocalDateTime dateTime) {
        return dateTime.getHour() +
                (dateTime.getMinute() / MINUTES) +
                (dateTime.getSecond() / SECONDS);
    }
}
