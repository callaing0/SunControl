package com.suncontrol.domain.dto;

import lombok.Data;

@Data // 이 어노테이션이 setLocation, setEfficiency 등을 만듭니다.
public class AlertDTO {
    private Long alertId;
    private String location;    // 위치
    private Double efficiency;  // 발전 효율
    private String status;      // 상태 (WARNING / NORMAL)
    private String message;     // 알림 메시지
}