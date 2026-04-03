package com.suncontrol.core.constant.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Province {
    DAEJEON("30", "대전광역시", "대전");

    private final String code; // 광역 코드
    private final String description; // 광역단체명
    private final String shortDesc; // 광역단체명 약칭

    /// 지역별 현황 조회 UI용 LIST
    public static final List<Province> LIST =
            Arrays.asList(Province.values());

    /// 지역별 빠른 조회용 MAP
    private static final Map<String, Province> CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            Province::getCode,
                            Function.identity()
                    )));

    /// 2자리 문자열 광역코드로 광역단체 리턴
    public static Province fromCode(String code) {
        return CODE_MAP.get(code);
    }
}