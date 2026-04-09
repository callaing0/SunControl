package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class AlertSaveRequestDTO {
    private String location;
    private String status;
    private Double efficiency; // 효율 저장 중심
    private String message;    // 메시지 저장 중심
}