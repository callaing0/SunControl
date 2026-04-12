package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.entity.log.GenerationLog;
import com.suncontrol.core.util.TimeTruncater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GenerationLogDto {
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치

    private Weather weather;
    private Integer weatherCode;

    private GenerationStatus generationStatus;
    private LocalDateTime updatedAt;

    public GenerationLogDto(GenerationLog entity) {
        this.inverterId = entity.getInverterId();
        this.baseTime = entity.getBaseTime();
        this.valueExpected = entity.getValueExpected();
        this.valueActual = entity.getValueActual();
        this.performanceRatio = entity.getPerformanceRatio();
        this.expectedRatio = entity.getExpectedRatio();
        this.capacityFactor = entity.getCapacityFactor();
        this.accumEnergy = entity.getAccumEnergy();
        this.weather = Weather.fromCode(entity.getWeatherCode());
        this.weatherCode = entity.getWeatherCode();
        this.generationStatus = GenerationStatus.fromStatus(entity.getStatus());
        this.updatedAt = entity.getUpdatedAt();
    }

    public LocalDateTime truncateBaseTime(int termSeconds) {
        return TimeTruncater.truncateToNextTerm(this.baseTime, termSeconds);
    }

    public GenerationValuesDto getValuesDto() {
        return new GenerationValuesDto
                (inverterId, baseTime,
                        valueExpected, valueActual, accumEnergy, performanceRatio,
                        accumEnergy, generationStatus);
    }
}