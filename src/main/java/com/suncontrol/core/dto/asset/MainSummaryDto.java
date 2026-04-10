package com.suncontrol.core.dto.asset;

import com.sun.tools.javac.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MainSummaryDto {
    private int count;
    private BigDecimal accumTotal;
    private int collectInterval;

    public MainSummaryDto (int count, BigDecimal accumTotal) {
        this(count,  accumTotal, 60);
    }
}
