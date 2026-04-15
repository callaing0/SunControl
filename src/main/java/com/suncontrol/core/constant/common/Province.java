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
    DAEJEON("30", "대전광역시", "대전"),
    SEOUL("11", "서울특별시", "서울"),
    INCHEON("28", "인천광역시", "인천"),
    GWANGJU("29", "광주광역시", "광주"),
    DAEGU("27", "대구광역시", "대구"),
    ULSAN("31", "울산광역시", "울산"),
    BUSAN("26", "부산광역시", "부산"),
    CHUNGBUK("43", "충청북도", "충북"),
    CHUNGNAM("44", "충청남도", "충남"),
    GANGWON("42", "강원도", "강원"),
    JEJU("50", "제주특별자치도", "제주");

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