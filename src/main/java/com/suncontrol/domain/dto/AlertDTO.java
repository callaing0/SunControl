package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class AlertDTO {
    private Long id;           // alert_id 매핑
    private String detectTime;  // 장애 감지 시간
    private String resolvedTime;// 조치 완료 시간
    private String location;    // 발전소 위치
    private String status;      // 상태 (장애/정상)
    private Double efficiency;  // 효율
}