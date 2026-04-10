package com.suncontrol.core.entity.log;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.dto.log.GenerationLogDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GenerationLog {
    private Long id;
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치


    private Integer weatherCode;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /// DB 저장 조회용 가상필드
    public GenerationLog(GenerationLogDto dto) {
        this.inverterId = dto.getInverterId();
        this.baseTime = dto.getBaseTime();
        this.valueExpected = dto.getValueExpected();
        this.valueActual = dto.getValueActual();
        this.performanceRatio = dto.getPerformanceRatio();
        this.expectedRatio = dto.getExpectedRatio();
        this.capacityFactor = dto.getCapacityFactor();
        this.accumEnergy = dto.getAccumEnergy();
        this.weatherCode = dto.getWeatherCode();
        this.status = dto.getGenerationStatus().getStatus();
    }
}
