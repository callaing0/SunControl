package com.suncontrol.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter, ToString 등을 자동 생성
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertDTO {
    private Long alertId;
    private String location;    // [체크] 이 필드가 있어야 setLocation 가능
    private Double efficiency;  // [체크] 이 필드가 있어야 setEfficiency 가능
    private String message;     // [체크] 이 필드가 있어야 setMessage 가능
    private String status;      // [체크] 이 필드가 있어야 setStatus 가능
}