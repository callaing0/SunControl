package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.common.dto.generate.GenerateValueDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/// 실제 기상모델 적용 기대값 생성기
@Component("REAL_EXPECTED")
public class ExpectedByWeatherUtil extends AbstractExpectedUtil {
    @Override
    public GenerateValueDto generateEnergy(
            LocalDateTime baseTime,
            InverterGenerationDto inv,
            GenerateCalcBase base,
            GenerateValueDto dto) {
        /// 시간 유효성 검사
        double timeValue = getTimeValue(baseTime);
        if(isNightTime(timeValue, base))
            return dto.zeroExpected();

        final double GTI_MAX = 1000.0;
        double gtiFactor = Math.max(base.gti(), 0.0) / GTI_MAX;
        dto.setValueExpected(getValueExpected(inv.getMeasuredCapacity(), gtiFactor));

        return dto;
    }
}
