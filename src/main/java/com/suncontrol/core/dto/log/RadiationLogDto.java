package com.suncontrol.core.dto.log;

import com.suncontrol.core.entity.log.RadiationLog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RadiationLogDto {
    /// Map<Long(plantId), RadLogDto> - 저장
    /// Map<Long,Map<L.D.T,RadLogDto>> - 데이터생성
    private LocalDateTime baseTime;
    private double gti;
    private double gtiInstance;

    public RadiationLogDto(RadiationLog entity) {
        this.baseTime = entity.getBaseTime();
        this.gti = entity.getGti();
        this.gtiInstance = entity.getGtiInstance();
    }
}
