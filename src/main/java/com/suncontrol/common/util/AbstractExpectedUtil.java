package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.common.dto.generate.GenerateValueDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AbstractExpectedUtil implements GenerateUtil{
    @Override
    public abstract GenerateValueDto generateEnergy(
            LocalDateTime baseTime,
            InverterGenerationDto inv,
            GenerateCalcBase base,
            GenerateValueDto dto);

    protected boolean isNightTime(double currentHr,
                                GenerateCalcBase base) {
        return currentHr > base.sunsetHr() ||
                currentHr < base.sunriseHr();
    }

    protected double getTimeValue(LocalDateTime baseTime) {
        return baseTime.getHour() +
                (baseTime.getMinute() / 60.0) +
                (baseTime.getSecond() / 3600.0);
    }

    protected BigDecimal getValueExpected(BigDecimal capacity, double gtiFactor) {
        return capacity.multiply(BigDecimal.valueOf(gtiFactor));
    }
}
