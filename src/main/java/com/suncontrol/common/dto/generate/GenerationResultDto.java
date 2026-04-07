package com.suncontrol.common.dto.generate;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.dto.log.GenerationLogDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString/// 테스트용
public class GenerationResultDto {
    /// 발전기록 DB 저장용 결과값
    private Long inverterId;
    private LocalDateTime baseTime;
    private GenerateValueDto values; /// 이 친구도 ToString 가지고 있어야 함
    private BigDecimal accumEnergy;
    private Weather weather;

    /// GenerationLogDto
    public GenerationLogDto getGenerationLogDto() {
        return GenerationLogDto.builder()
                .inverterId(inverterId)
                .baseTime(baseTime)
                .valueExpected(values.getValueExpected())
                .valueActual(values.getValueActual())
                .performanceRatio(values.getPerformanceRatio())
                .expectedRatio(values.getExpectedRatio())
                .capacityFactor(values.getCapacityFactor())
                .accumEnergy(accumEnergy)
                .weather(weather)
                .generationStatus(GenerationStatus.PENDING)
                .build();
    }
}
