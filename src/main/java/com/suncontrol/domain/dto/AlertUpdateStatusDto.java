package com.suncontrol.domain.dto;

import com.suncontrol.core.constant.alert.AlertStatus; // PENDING, CHECKING 등
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AlertUpdateStatusDto {
    private Long inverterId;
    private LocalDateTime baseTime;
    private AlertStatus status;
}