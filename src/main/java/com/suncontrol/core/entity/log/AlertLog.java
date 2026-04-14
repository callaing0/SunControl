package com.suncontrol.core.entity.log;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AlertLog {
    private Long id;
    private Long inverterId;
    private int status;
    private int alertType;
    private int severity;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime checkedAt;
    private LocalDateTime resolvedAt;
}
