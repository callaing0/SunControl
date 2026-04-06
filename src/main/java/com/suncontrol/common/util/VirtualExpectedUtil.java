package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.core.dto.asset.InverterGenerationDto;
import com.suncontrol.core.dto.log.component.GenerateValueDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/// 가상 일사량 및 기대값 생성 인터페이스 구현체
@Component("VIRTUAL_EXPECTED")
public class VirtualExpectedUtil implements GenerateUtil{

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
