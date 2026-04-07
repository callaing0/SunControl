package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.GenerateDataContext;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.common.dto.generate.GenerateValueDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/// 가상 일사량 및 기대값 생성 인터페이스 구현체
@Component("VIRTUAL_EXPECTED")
public class VirtualExpectedUtil extends AbstractExpectedUtil {

    @Override
    public GenerateDataContext generateEnergy(
            GenerateDataContext context) {
        LocalDateTime baseTime = context.getCurrent();
        GenerateCalcBase base = context.getBase();
        GenerateValueDto dto = context.getDto();
        /// 시간 유효성 검사
        double timeValue = getTimeValue(baseTime);
        if (isNightTime(timeValue, base)) {
            context.setDto(dto.zeroExpected());
            return context;
        }
        InverterGenerationDto inv = context.getInv();

        double progress = (timeValue - base.sunriseHr()) /
                (base.sunsetHr() - base.sunriseHr());
        double radian = progress * Math.PI;
        /// 이곳에는 gti 값이 0으로 들어오기 때문에 사인^3함수로
        double gtiFactor = Math.max(Math.pow(Math.sin(radian), 3), 0.0);
        dto.setValueExpected(getValueExpected(inv.getMeasuredCapacity(), gtiFactor));
        context.setDto(dto);

        return context;
    }
}
