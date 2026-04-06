package com.suncontrol.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class TimeTruncater {

    public static LocalDateTime truncateToNextTerm(LocalDateTime time, int termSecond) {
        return truncateToTerm(time,termSecond).plusSeconds(termSecond).withNano(0);
    }

    public static LocalDateTime truncateToTerm(LocalDateTime time, int termSecond) {
        LocalDateTime dayStart = time.truncatedTo(ChronoUnit.DAYS);
        long secondsOfDay = ChronoUnit.SECONDS.between(dayStart, time);

        long nextTermSeconds = (secondsOfDay / termSecond) * termSecond;

        return dayStart.plusSeconds(nextTermSeconds).withNano(0);
    }
}
