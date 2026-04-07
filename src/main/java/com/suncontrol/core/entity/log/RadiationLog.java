package com.suncontrol.core.entity.log;

import com.suncontrol.core.dto.log.RadiationLogDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RadiationLog {
    private Long id;
    private Long plantId;
    private LocalDateTime baseTime;
    private double gti;
    private double gtiInstance;
    private Integer dayOffset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RadiationLog(RadiationLogDto dto) {
        this.plantId = dto.getPlantId();
        this.baseTime = dto.getBaseTime();
        this.gti = dto.getGti();
        this.gtiInstance = dto.getGtiInstance();
        this.dayOffset = dto.getDayOffset();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}
