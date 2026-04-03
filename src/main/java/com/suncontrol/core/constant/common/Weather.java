package com.suncontrol.core.constant.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Weather {
    CLEAR_SKY(0, "맑음", 1.0),
    CLOUDY(1, "흐림", 0.8),
    DRIZZLE(51, "이슬비", 0.5),
    RAIN(61, "비", 0.3),
    SNOW(71, "눈", 0.3),
    RAIN_SHOWERS(80, "폭우", 0.2),
    THUNDERSTORM(95, "뇌우", 0.1);

    private final int wmo;
    private final String description;
    private final double efficiencyFactor; // 발전량 가중치

    public static Weather fromCode(String code) {
        if (code == null || code.isBlank()) {
            return CLEAR_SKY;
        }

        try {
            int wmoInt = Integer.parseInt(code.trim());
            return Arrays.stream(Weather.values())
                    .filter(wc -> wc.wmo == wmoInt)
                    .findFirst()
                    .orElse(CLEAR_SKY);
        } catch (NumberFormatException e) {
            return CLEAR_SKY;
        }
    }

    public String getWeatherCode() {
        return String.format("%02d", this.wmo);
    }
}
