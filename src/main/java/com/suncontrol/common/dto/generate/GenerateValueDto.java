package com.suncontrol.common.dto.generate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@ToString
public class GenerateValueDto {
    /// 발전 기록/통계 용 값 부속품
    private BigDecimal valueExpected;
    private BigDecimal valueActual;
    @Setter(AccessLevel.NONE)
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

    public GenerateValueDto zeroExpected() {
        this.valueExpected = BigDecimal.ZERO;

        return this;
    }

    public GenerateValueDto zeroActual() {
        this.valueActual = BigDecimal.ZERO;

        return this;
    }

    public void setCapacity(BigDecimal ratCap, BigDecimal measCap) {
        this.capacity = ratCap.min(measCap);
        this.valueExpected = this.capacity.min(
                valueExpected != null ? valueExpected : BigDecimal.ZERO);
        this.valueActual = this.capacity.min(
                valueActual != null ? valueActual : BigDecimal.ZERO);
    }
}
