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
    private Integer weatherCode;

    /// GenerationLogDto
    public GenerationLogDto getGenerationLogDto() {
        GenerationLogDto dto = new GenerationLogDto();

        dto.setInverterId(inverterId);
        dto.setBaseTime(baseTime);
        dto.setValueExpected(values.getValueExpected());
        dto.setValueActual(values.getValueActual());
        dto.setPerformanceRatio(values.getPerformanceRatio());
        dto.setExpectedRatio(values.getExpectedRatio());
        dto.setCapacityFactor(values.getCapacityFactor());
        dto.setAccumEnergy(accumEnergy);
        dto.setWeatherCode(weatherCode);
        dto.setGenerationStatus(GenerationStatus.PENDING);

        return dto;
    }
}
