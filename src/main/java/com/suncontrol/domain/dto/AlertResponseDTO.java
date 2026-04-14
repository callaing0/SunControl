package com.suncontrol.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
// AlertResponseDTO.java
public class AlertResponseDTO {
    private Long id;
    private Integer status;       // ✅ 반드시 Integer (Enum 사용 금지)
    private Integer alertType;    // ✅ 반드시 Integer
    private String statusDescription; // 이 필드가 '장애'/'정상' 글자를 담을 곳입니다.

    // ✅ 만약 롬복 @Data가 없다면 Getter/Setter를 꼭 만드세요.
    public String getStatusDescription() {
        if (this.status == null) return "알 수 없음";
        return (this.status == 0) ? "장애" : "정상"; // 0이면 장애, 1이면 정상
    }
}