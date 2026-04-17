package com.suncontrol.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponseDTO {
    private Long id;
    private Long inverterId;
    private String location;   // ✅ 에러 원인: 이 필드를 반드시 추가해야 합니다.

    private Integer status;    // 0: 대기, 1: 정상
    private Integer alertType;
    private Integer severity;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime resolvedAt;

    // LOG ID 칸에 보일 인버터 번호 매핑
    public String getInverterNumber() {
        if (this.inverterId == null) return "-";
        if (this.inverterId == 1) return "260401";
        return String.valueOf(this.inverterId);
    }

    // 상태 설명: 0일 때 '대기'로 표시
    public String getStatusDescription() {
        if (this.status == null) return "데이터 없음";
        if (this.status == 0) return "대기";
        return "정상";
    }
}