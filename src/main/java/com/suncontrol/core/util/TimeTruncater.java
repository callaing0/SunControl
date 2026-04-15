package com.suncontrol.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

@Component
public class TimeTruncater {

    public static LocalDateTime toReportCeiling(LocalDateTime time, int termSecond) {
        LocalDateTime truncated = truncateToTerm(time, termSecond);

        if(time.isEqual(truncated)) {
            return truncated.withNano(0);
        }

        return truncated.plusSeconds(termSecond).withNano(0);
    }

    public static LocalDateTime truncateToPreviousTerm(LocalDateTime time, int termSecond) {
        return truncateToTerm(time, termSecond).minusSeconds(termSecond).withNano(0);
    }

    public static LocalDateTime truncateToNextTerm(LocalDateTime time, int termSecond) {
        return truncateToTerm(time, termSecond).plusSeconds(termSecond).withNano(0);
    }

    public static LocalDateTime truncateToTerm(LocalDateTime time, int termSecond) {
        LocalDateTime dayStart = time.truncatedTo(ChronoUnit.DAYS);
        long secondsOfDay = ChronoUnit.SECONDS.between(dayStart, time);

        long currentTermSeconds = (secondsOfDay / termSecond) * termSecond;

        return dayStart.plusSeconds(currentTermSeconds).withNano(0);
    }

    public static String getBaseMonth(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public static String getBaseMonth(LocalDateTime time) {
        return getBaseMonth(time.toLocalDate());
    }

    public static <T> LocalDate getOldestDateOrDefault(
            Collection<T> collection,
            LocalDate defaultValue,
            Function<T, LocalDate> function
    ) {
        return collection.stream()
                .map(function)
                .min(Comparator.naturalOrder())
                .orElse(defaultValue);
    }

    public static LocalDate getOldestDateOrDefault(
            Collection<LocalDate> collection,
            LocalDate defaultValue
    ) {
        return getOldestDateOrDefault(collection, defaultValue, Function.identity());
    }

    public static LocalDate getOlderDate(
            LocalDate value,
            LocalDate defaultValue
    ) {
        return value.isBefore(defaultValue) ? value : defaultValue;
    }


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
