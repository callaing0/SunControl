package com.suncontrol.core.dto.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerationValuesDto {
    /// 발전 통계용
    private Long inverterId;
    private LocalDateTime baseTime;
    private BigDecimal valueExpected;
    private BigDecimal valueActual;
    private BigDecimal valuePrevious;
    private BigDecimal accumEnergy;
}
