package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class AlertDTO {
    // 조회용 필드
    private Long id;
    private String detectTime;
    private String resolvedTime;

    // 공통 및 저장용 필드
    private String location;
    private String status;
    private Double efficiency; // 효율 중심
    private String message;    // 메시지 중심
}
