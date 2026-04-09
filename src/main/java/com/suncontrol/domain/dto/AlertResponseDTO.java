package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class AlertResponseDTO {
    private Long id;
    private String detectTime;   // 이력 조회 중심
    private String resolvedTime; // 이력 조회 중심
    private String location;
    private String status;
}
