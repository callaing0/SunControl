package com.suncontrol.core.dto.asset;

import com.sun.tools.javac.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@AllArgsConstructor
public class MainSummaryDto {
    private int count;
    private BigDecimal accumTotal;
    private int collectInterval;

    public String getAccumTotalFormatted (){
        return accumTotal.setScale(2, RoundingMode.HALF_UP).toString() + " kWh";
    }

    public MainSummaryDto (int count, BigDecimal accumTotal) {
        this(count, accumTotal, 60);
    }
}
