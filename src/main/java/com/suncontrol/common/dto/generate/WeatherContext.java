package com.suncontrol.common.dto.generate;

import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public record WeatherContext(Map<LocalDateTime, WeatherLogDto> weatherLogs,
                             Map<LocalDateTime, RadiationLogDto> radiationLogs,
                             Map<LocalDate, DailyWeatherDto> dailyWeathers) {
    public GenerateCalcBase getGenerateCalcBase(LocalDateTime time) {
        return new GenerateCalcBase(
                weatherLogs.get(time),
                radiationLogs.get(time),
                dailyWeathers.get(time.toLocalDate())
        );
    }
}
