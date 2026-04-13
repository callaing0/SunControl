package com.suncontrol.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SafeDivider {

    public static BigDecimal ratioDivide(BigDecimal numerator, BigDecimal denominator) {
        if(invalidChecker(denominator)) {
            return BigDecimal.ZERO;
        }

        return numerator.divide(denominator, 3, RoundingMode.HALF_UP);
    }

    private static boolean invalidChecker(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }
}
