package com.suncontrol.core.constant.alert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlertStatus {
    PENDING(0,"장애 발생"),
    PROCESSING(1,"확인 중"),
    RESOLVED(2,"조치완료");

    private final int code;
    private final String description;

    public static AlertStatus fromCode(int code) {
        for (AlertStatus status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public AlertStatus next() {
        switch (this) {
            case PENDING: return PROCESSING;
            case PROCESSING: return RESOLVED;
            default: return this;
        }
    }
}
