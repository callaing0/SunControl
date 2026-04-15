package com.suncontrol.core.constant.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlertStatus {
    ABNORMAL(1, "이상 발생"),
    CHECKING(2, "확인중"),
    RESOLVED(3, "해결완료");

    private final int code;
    private final String label;

    public static AlertStatus fromCode(int code) {
        for (AlertStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("알 수 없는 AlertStatus code: " + code);
    }

    public AlertStatus next() {
        return switch (this) {
            case ABNORMAL -> CHECKING;
            case CHECKING -> RESOLVED;
            case RESOLVED -> RESOLVED;
        };
    }
}
