package com.suncontrol.common.dto.report;

import com.suncontrol.core.constant.util.GenerationStatus;
import com.suncontrol.core.constant.util.StaticValues;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.dto.component.StoppedDto;
import com.suncontrol.core.util.SafeDivider;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReportStoppedCalcDto extends ReportCalcDto{

    public ReportStoppedCalcDto(LocalDateTime baseTime, BigDecimal previous, List<GenerationValuesDto> list, int baseTerm, LocalDateTime createdAt) {
        super(baseTime, previous, list, baseTerm, createdAt);
    }

    public StoppedDto getStoppedDto() {
        StoppedDto dto = new StoppedDto(0, 0);

        boolean isLinked = false;
        LocalDateTime prevTime = super.getPrevTime();

        for(GenerationValuesDto value : super.getList()) {
            GenerationStatus status = value.getGenerationStatus();
            long term = Duration.between(prevTime, value.getBaseTime()).getSeconds();

            if(status == GenerationStatus.ERROR) {
                /// 정지 회수가 증가하는 조건 체크
                if(!isLinked) {
                    isLinked = true;
                    dto.setIncidentCount(dto.getIncidentCount() + 1);
                }

                /// 오류상태라면 반드시 가동정지시간이 증가해야 한다.
                /// BASE_RATIO 대비 performanceRatio를 구하면
                /// 정상가동이 되었던
                if(value.getPerformanceRatio() != null) {
                    BigDecimal time = value.getPerformanceRatio()
                            .divide(StaticValues.BASE_RATIO, 5, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(term));
                    dto.setStoppedTime(dto.getStoppedTime() + time.intValue());
                } else {
                    dto.setStoppedTime(dto.getStoppedTime() + (int)(term));
                }

            } else if(status == GenerationStatus.IDLE || status == GenerationStatus.NORMAL) {
                isLinked = false;
            }
            prevTime = value.getBaseTime();
        }

        return dto;
    }

    @Override
    public GenerationValuesDto getValues() {
        GenerationValuesDto dto = getBaseValues();
        BigDecimal actual = BigDecimal.ZERO;
        BigDecimal expected = BigDecimal.ZERO;

        for(GenerationValuesDto value : super.getList()) {
            actual = actual.add(value.getValueActual());
            expected = expected.add(value.getValueExpected());
        }
        dto.setValueActual(actual.setScale(4, RoundingMode.HALF_UP));
        dto.setValueExpected(expected.setScale(4, RoundingMode.HALF_UP));

        BigDecimal performanceRatio = SafeDivider.ratioDivide(actual, expected);

        dto.setPerformanceRatio(performanceRatio);

        dto.setGenerationStatus(GenerationStatus.fromRatio(expected, performanceRatio, StaticValues.BASE_RATIO));

        return dto;
    }
}
