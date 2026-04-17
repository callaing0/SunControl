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
            new BigDecimal("36.325781"), new BigDecimal("127.42133")),
    DAEJEON_SEO("30170", Province.DAEJEON, "서구",
            new BigDecimal("36.355498"), new BigDecimal("127.38378")),
    DAEJEON_DONG("30110", Province.DAEJEON, "동구",
            new BigDecimal("36.311776"), new BigDecimal("127.45480")),
    DAEJEON_YUSEONG("30200", Province.DAEJEON, "유성구",
            new BigDecimal("36.362285"), new BigDecimal("127.35625")),
    DAEJEON_DAEDEOK("30230", Province.DAEJEON, "대덕구",
            new BigDecimal("36.346705"), new BigDecimal("127.41556")),

    SEOUL_GANGNAM("11680", Province.SEOUL, "강남구",
            new BigDecimal("37.517236"), new BigDecimal("127.047325")),

    CHUNGNAM_CHEONAN("44131", Province.CHUNGNAM, "천안시 동남구",
            new BigDecimal("36.815147"), new BigDecimal("127.113893")),

    CHUNGBUK_CHEONGJU("43111", Province.CHUNGBUK, "청주시 상당구",
            new BigDecimal("36.635684"), new BigDecimal("127.49167")),

    INCHEON_NAMDONG("28200", Province.INCHEON, "남동구",
            new BigDecimal("37.447368"), new BigDecimal("126.731620")),

    GANGWON_CHUNCHEON("42110", Province.GANGWON, "춘천시",
            new BigDecimal("37.881315"), new BigDecimal("127.729970")),

    BUSAN_HAEUNDAE("26350", Province.BUSAN, "해운대구",
            new BigDecimal("35.163177"), new BigDecimal("129.163634")),

    DAEGU_SUSEONG("27260", Province.DAEGU, "수성구",
            new BigDecimal("35.858165"), new BigDecimal("128.630625")),

    GWANGJU_BUKGU("29170", Province.GWANGJU, "북구",
            new BigDecimal("35.174047"), new BigDecimal("126.911963")),

    ULSAN_NAMGU("31140", Province.ULSAN, "남구",
            new BigDecimal("35.543798"), new BigDecimal("129.330109")),

    JEJU_JEJU("50110", Province.JEJU, "제주시",
            new BigDecimal("33.499621"), new BigDecimal("126.531188")),

    OUT_OF_SERVICE(null, null, "서비스 지역 이탈",
            null, null);
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
        return CODE_MAP.getOrDefault(code, OUT_OF_SERVICE);
    }
}
