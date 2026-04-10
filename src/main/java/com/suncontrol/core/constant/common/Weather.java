package com.suncontrol.core.constant.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

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

    private final int wmo; // 국제표준 2자리숫자 코드
    private final String description; // UI에 표시될 문자열
    private final double efficiencyFactor; // 발전량 통계 분석을 위한 기상 가중치

    public static Weather fromCode(Integer code) {
        if (code == null) {
            return CLEAR_SKY;
        }

        try {
            return Arrays.stream(Weather.values())
                    .sorted(Comparator
                            .comparingInt((Weather w) -> w.wmo)
                            .reversed()
                    )
                    .filter(wc -> wc.wmo <= code)
                    .findFirst()
                    .orElse(CLEAR_SKY);
        } catch (NumberFormatException e) {
            return CLEAR_SKY;
        }
    }

    public Integer getWeatherCode() {
        return this.wmo;
    }
}
