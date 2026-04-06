package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.core.dto.log.component.GenerateValueDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/// 실제 기상모델 적용 기대값 생성기
@Component("REAL_EXPECTED")
public class ExpectedByWeatherUtil implements GenerateUtil{
    @Override
    public GenerateValueDto generateEnergy(
            LocalDateTime baseTime,
            InverterGenerationDto inv,
            GenerateCalcBase base,
            GenerateValueDto dto) {
        // todo
        return null;
    }
}
