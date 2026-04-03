package com.suncontrol.core.constant.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum PanelModel {
    /**
     * 패널 모델
     * */

    DUMMY_A("DUMMY-A", 500, BigDecimal.valueOf(20.0)),
    DUMMY_B("DUMMY-B", 450, BigDecimal.valueOf(20.0)),
    DUMMY_C("DUMMY-C", 400, BigDecimal.valueOf(20.0));

    private final String name;
    private final int capacity;
    private final BigDecimal efficiency;

    /// UI표시용 리스트
    public static final List<PanelModel> LIST =
            Arrays.asList(PanelModel.values());

    /// 이름조회용 맵
    private static final Map<String, PanelModel> LABEL_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            PanelModel::getName,
                            Function.identity()
                    )));

    public static PanelModel getByName(String name) {
        return LABEL_MAP.get(name);
    }
}
