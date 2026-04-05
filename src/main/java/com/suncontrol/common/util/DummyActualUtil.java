package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.core.dto.asset.InverterGenerationDto;
import com.suncontrol.core.dto.log.component.GenerateValueDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/// 더미 발전기록 생성 인터페이스 구현체
@Component("DUMMY_ACTUAL")
public class DummyActualUtil implements GenerateUtil {

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
