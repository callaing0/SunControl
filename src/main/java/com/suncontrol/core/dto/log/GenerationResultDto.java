package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.dto.log.component.GenerateValueDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString/// 테스트용
public class GenerationResultDto {
    /// 발전기록 DB 저장용 결과값
    private Long inverterId;
    private LocalDateTime baseTime;
    private GenerateValueDto values;
    private BigDecimal accumEnergy;
    private Weather weather;

    /// Entity 생성자를 위한 가상 필드 메서드
    public BigDecimal getValueExpected() {
        return values == null ? null : values.getValueExpected();
    }
    public BigDecimal getValueActual() {
        return values == null ? null : values.getValueActual();
    }
    public BigDecimal getPerformanceRatio() {
        return values == null ? null : values.getPerformanceRatio();
    }
    public BigDecimal getExpectedRatio() {
        return values == null ? null : values.getExpectedRatio();
    }
    public BigDecimal getCapacityFactor() {
        return values == null ? null : values.getCapacityFactor();
    }
}
