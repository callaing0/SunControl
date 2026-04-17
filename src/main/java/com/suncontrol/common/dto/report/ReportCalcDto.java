package com.suncontrol.common.dto.report;

import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.util.SafeDivider;
import com.suncontrol.core.util.TimeTruncater;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class ReportCalcDto {
    private final LocalDateTime baseTime;
    private final BigDecimal previous;
    private final List<GenerationValuesDto> list;
    private final int baseTerm;
    private final LocalDateTime prevTime;

    protected GenerationValuesDto getBaseValues() {
        GenerationValuesDto dto = new GenerationValuesDto();

        // previous는 null 일 수 있으므로 list의 0번 인덱스에서 inverterId를 꺼낸다.
        dto.setInverterId(list.get(0).getInverterId());
        dto.setBaseTime(baseTime);
        dto.setValuePrevious(previous != null ? previous : BigDecimal.ZERO);
        dto.setAccumEnergy(list.get(list.size() - 1).getAccumEnergy());

        return dto;
    }

    public GenerationValuesDto getValues() {
        GenerationValuesDto dto = getBaseValues();
        BigDecimal actual = BigDecimal.ZERO;
        BigDecimal expected = BigDecimal.ZERO;
        LocalDateTime prevTime = this.prevTime;

        for(GenerationValuesDto value : list) {
            long term = Duration.between(prevTime, value.getBaseTime()).getSeconds();
            BigDecimal weight = BigDecimal.valueOf(term)
                    .divide(BigDecimal.valueOf(baseTerm), 10, RoundingMode.HALF_UP);

            actual = actual.add(value.getValueActual().multiply(weight));
            expected = expected.add(value.getValueExpected().multiply(weight));

            prevTime = value.getBaseTime();
        }
        dto.setValueActual(actual.setScale(4, RoundingMode.HALF_UP));
        dto.setValueExpected(expected.setScale(4, RoundingMode.HALF_UP));

        BigDecimal performanceRatio = SafeDivider.ratioDivide(actual, expected);

        dto.setPerformanceRatio(performanceRatio);

        dto.setGenerationStatus(GenerationStatus.fromRatio(expected, performanceRatio, StaticValues.BASE_RATIO));

        return dto;
    }
}
