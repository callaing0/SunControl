package com.suncontrol.core.constant.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum PanelManufacturer {
    /**
     * 패널 제조사
     * */

    DUMMY_CORP("DUMMY-CORP");

    private final String label;

    /// label 기준으로 UI표시용 List
    public static final List<PanelManufacturer> LIST =
            Arrays.asList(PanelManufacturer.values());

    private static final Map<String, PanelManufacturer> MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            PanelManufacturer::getLabel,
                            Function.identity()
                    )));

    public static PanelManufacturer getByLabel(String label) {
        return MAP.get(label);
    }
}