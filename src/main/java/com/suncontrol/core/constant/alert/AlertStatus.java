package com.suncontrol.core.constant.alert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlertStatus {
    PENDING(0,"대기"),      // 신규 발생, 아직 아무도 확인 안 함
    PROCESSING(1,"확인 중"),  // 관리자가 인지하고 조치 중
    RESOLVED(2,"조치완료");     // 조치 완료 및 상황 종료

    private final int code;

    @com.fasterxml.jackson.annotation.JsonValue
    private final String description;
}
