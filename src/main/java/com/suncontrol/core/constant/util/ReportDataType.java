package com.suncontrol.core.constant.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportDataType {
    /**
     * HourlyReport, DailyReport에
     * 7일전, 1일전, 당일 스냅샷 구분하는 상태값
     * */

    FORECAST_7D(7, "7일 전 예측", "주간 예보"),
    FORECAST_1D(1, "1일 전 예측", "일간 예보"),
    ACTUAL_SNAPSHOT(0, "당일 실측", "당일 정보"),
    UNKNOWN(-1, "알 수 없음", "알 수 없음");

    private final Integer dayOffset; /// DB 저장 값
    private final String reportDescription; /// 화면 표시 - 통계
    private final String weatherDescription; /// 화면 표시 - 날씨

    public static ReportDataType findByDayOffset(Integer value) {
        /// 당일 = 0
        /// 1일 전 = 1
        /// 7일 전 = 2~7
        /// 그 외 = 알 수 없음
        if(value == null) return ReportDataType.UNKNOWN;

        if(value.equals(0)) return ReportDataType.ACTUAL_SNAPSHOT;
        if(value.equals(1)) return ReportDataType.FORECAST_1D;
        if(value >= 2 && value <= 7) return ReportDataType.FORECAST_7D;

        return UNKNOWN;
    }
}
