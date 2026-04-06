package com.suncontrol.core.constant.asset;

import com.suncontrol.core.constant.util.GenerationStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum InverterType {

    /**
     * 인버터 종류에 대한 Enum
     * */

    DUMMY("DUMMY", new BigDecimal("100.0"), GenerationStrategy.DUMMY_ACT);
    /// TODO : 추후 새로운 분류 추가 가능

    private final String label;
    private final BigDecimal efficiency;
    private final GenerationStrategy strategy;

    /// label 기준으로 UI표시용 LIST 만들기
    public static final List<InverterType> LIST =
            Arrays.asList(InverterType.values());

    /// label 기준으로 객체찾기용 MAP 만들기
    private static final Map<String, InverterType> MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            InverterType::getLabel,
                            Function.identity())));

    /// MAP에서 label 로 찾아서 반환
    public static InverterType fromLabel(String label) {
        return MAP.get(label);
    }
}
