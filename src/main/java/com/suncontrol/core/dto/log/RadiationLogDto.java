package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.entity.log.RadiationLog;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RadiationLogDto {
    /// List<RadLogDto> - 저장
    /// Map<발전소ID,Map<시간,RadLogDto>> - 데이터생성
    private Long plantId;
    private LocalDateTime baseTime;
    private ReportDataType dataType;

    private double gti;
    private double gtiInstant;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RadiationLogDto(RadiationLog entity) {
        this.plantId = entity.getPlantId();
        this.baseTime = entity.getBaseTime();
        this.gti = entity.getGti();
        this.gtiInstant = entity.getGtiInstant();
        this.dataType = ReportDataType.findByDayOffset(entity.getDayOffset());
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public Integer getDayOffset() {
        return dataType != null ? this.dataType.getDayOffset() : ReportDataType.UNKNOWN.getDayOffset();
    }
}
