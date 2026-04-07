package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.entity.log.RadiationLog;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RadiationLogDto {
    /// Map<Long(plantId), RadLogDto> - 저장
    /// Map<Long,Map<L.D.T,RadLogDto>> - 데이터생성
    private Long plantId;
    private LocalDateTime baseTime;
    private double gti;
    private double gtiInstance;
    private ReportDataType dataType;

    public RadiationLogDto(RadiationLog entity) {
        this.plantId = entity.getPlantId();
        this.baseTime = entity.getBaseTime();
        this.gti = entity.getGti();
        this.gtiInstance = entity.getGtiInstance();
        this.dataType = ReportDataType.findByDayOffset(entity.getDayOffset());
    }

    public Integer getDayOffset() {
        return dataType != null ? this.dataType.getDayOffset() : ReportDataType.UNKNOWN.getDayOffset();
    }
}
