package com.suncontrol.domain.dto;

import com.suncontrol.core.constant.alert.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponseDTO {
    private Long id;
    private Long inverterId;
    private String location;     // HTML의 alert.location과 매칭
    private AlertStatus status;  // EnumOrdinalTypeHandler가 처리
    private Integer alertType;
    private Integer severity;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
    private Integer durationSeconds;

    // 화면에서 '대기', '조치완료' 등을 출력할 때 사용: [[${alert.statusDescription}]]
    public String getStatusDescription() {
        return (status != null) ? status.getDescription() : "알 수 없음";
    }
}