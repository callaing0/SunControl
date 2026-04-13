package com.suncontrol.core.constant.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GenerationStatus {
    PENDING("PENDING"),
    NORMAL("NORMAL"),
    ERROR("FAULT"),
    IDLE("IDLE");

    private final String status;

    private static final Map<String, GenerationStatus> STATUS_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            GenerationStatus::getStatus,
                            Function.identity())));

    public static GenerationStatus fromStatus(String status) {
        return STATUS_MAP.get(status);
    }

    public static GenerationStatus fromRatio
            (BigDecimal expected, BigDecimal ratio, BigDecimal baseRatio) {
        if(expected.compareTo(BigDecimal.ZERO) <= 0) {
            return IDLE;
        }

        return ratio.compareTo(baseRatio) >=0 ? NORMAL : ERROR;
    }
}
