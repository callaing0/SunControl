package com.suncontrol.core.constant.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 데이터 생성모델 EnumMap 매핑용 Enum
 * */
@Getter
@AllArgsConstructor
public enum GenerationStrategy {

    /** 날씨정보가 없을 경우 가상 기대값을 산출하는 엔진
     * GenerationCalcBase.expStrategy에 보관
     * */
    VIRTUAL_EXP("VIRTUAL_EXPECTED"),
    /** 날씨정보가 있을 경우 기대값을 산출하는 엔진
     * GenerationCalcBase.expStrategy에 보관
     * */
    REAL_EXP("REAL_EXPECTED"),
    /** DUMMY 인버터의 실측값을 산출하는 엔진
     * InverterGenerationDto.InverterType.strategy에 보관
     * */
    DUMMY_ACT("DUMMY_ACTUAL");
    // TODO : 실제 모델이 들어올 경우 새로운 구현체도 함께 구현할 것


    private final String label;
}