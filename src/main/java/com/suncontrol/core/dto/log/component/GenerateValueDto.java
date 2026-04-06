package com.suncontrol.core.dto.log.component;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class GenerateValueDto {
    /// 발전 기록/통계 용 값 부속품
    private BigDecimal valueExpected;
    private BigDecimal valueActual;
    private BigDecimal capacity;

    public BigDecimal getPerformanceRatio() {
        if(valueActual == null || valueActual.compareTo(BigDecimal.ZERO) == 0)
            return null;
        return valueActual.divide(valueExpected, 5, RoundingMode.HALF_UP)
                .movePointRight(2);
    }

    public BigDecimal getExpectedRatio() {
        if(capacity == null || capacity.compareTo(BigDecimal.ZERO) == 0)
            return null;
        return valueExpected.divide(capacity, 5, RoundingMode.HALF_UP)
                .movePointRight(2);
    }

    public BigDecimal getCapacityFactor() {
        if(capacity == null || capacity.compareTo(BigDecimal.ZERO) == 0)
            return null;
        return valueActual.divide(capacity, 5, RoundingMode.HALF_UP)
                .movePointRight(2);
    }
}
