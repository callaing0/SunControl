package com.suncontrol.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertResponseDTO {
    private Long id;
    private Long inverterId;
    private Integer status;
    private Integer alertType;
    private Integer severity;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime checkedAt;
    private LocalDateTime resolvedAt;

    private String location;
    private Double efficiency;
}