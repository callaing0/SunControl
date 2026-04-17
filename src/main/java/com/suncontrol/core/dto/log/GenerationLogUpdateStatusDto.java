package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.util.GenerationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GenerationLogUpdateStatusDto {
    private Long inverterId;
    private LocalDateTime baseTime;
    private GenerationStatus generationStatus;

    public String getStatus() {
        return generationStatus.getStatus();
    }
}
