package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.GenerationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
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

    private GenerationStatus generationStatus;
    private LocalDateTime updatedAt;
}