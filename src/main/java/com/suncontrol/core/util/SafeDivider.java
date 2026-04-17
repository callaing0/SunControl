package com.suncontrol.core.util;

import com.suncontrol.core.constant.util.StaticValues;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SafeDivider {

    public static BigDecimal ratioDivide(BigDecimal numerator, BigDecimal denominator) {
        if(invalidChecker(denominator)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = numerator.divide(denominator, 5, RoundingMode.HALF_UP).movePointRight(2).setScale(2, RoundingMode.HALF_UP);

        return clamp(result);
    }

    private static boolean invalidChecker(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }

    private static BigDecimal clamp(BigDecimal value) {
        if(value.compareTo(StaticValues.MAX_RATIO) > 0) {
            return StaticValues.MAX_RATIO;
        }
        if(value.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return value;
    }
}
