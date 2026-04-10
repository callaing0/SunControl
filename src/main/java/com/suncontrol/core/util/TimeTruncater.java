package com.suncontrol.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

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

    ///
    public static <T> LocalDateTime getOldestTimeOrDefault(
            Collection<T> collection,
            LocalDateTime defaultValue,
            Function<T, LocalDateTime> function
    ) {
        return collection.stream()
                .map(function)
                .min(Comparator.naturalOrder())
                .orElse(defaultValue);
    }

    public static <K> LocalDateTime getOldestTimeOrDefault(
            Map<K, LocalDateTime> map,
            LocalDateTime defaultValue
    ) {
        return getOldestTimeOrDefault(map.values(), defaultValue, Function.identity());
    }

    public static LocalDateTime getOldestTimeOrDefault
            (Collection<LocalDateTime> collection, LocalDateTime defaultValue) {
        return getOldestTimeOrDefault
                (collection, defaultValue, Function.identity());
    }
}
