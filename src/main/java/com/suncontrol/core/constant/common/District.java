package com.suncontrol.core.constant.common;

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
public enum District {
    DAEJEON_JUNG("30140", Province.DAEJEON, "중구",
            new BigDecimal("36.325781"), new BigDecimal("127.42133"));
    // TODO: 다른 행정구역도 추가

    private final String code; // 지역 코드(기초단체 행정동코드 5자리)
    private final Province province; // 광역 코드
    private final String description; // 지역명
    private final BigDecimal latitude; // 위도
    private final BigDecimal longitude; // 경도

    public final static String TIMEZONE = "Asia/Tokyo"; // 기상조회용 시간대

    public String getRegionShortName() {
        return province.getShortDesc() + " " + description;
    }

    public String getRegionFullName() {
        return province.getDescription() + " " + description;
    }

    /// 지역별 현황 조회 UI용 LIST
    public static final List<District> LIST =
            Arrays.asList(District.values());

    /// 지역별 빠른 조회용 맵
    private static final Map<String, District> CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            District::getCode,
                            Function.identity()
                    )));

    /// 5자리 문자열 지역코드로 기초단체 리턴
    public static District fromCode(String code) {
        return CODE_MAP.get(code);
    }
}
