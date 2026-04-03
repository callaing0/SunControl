package com.suncontrol.core.constant.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenerationStrategy {
    /**
     * 데이터 생성모델 EnumMap 매핑용 Enum
     * */
    VIRTUAL_EXP("VIRTUAL_EXPECTED"),
    REAL_EXP("REAL_EXPECTED"),
    DUMMY_ACT("DUMMY_ACTUAL");
    // TODO : 실제 모델이 들어올 경우 새로운 구현체도 함께 구현할 것


    private final String label;
}