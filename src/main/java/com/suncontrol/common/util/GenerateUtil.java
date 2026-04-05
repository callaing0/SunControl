package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.core.dto.asset.InverterGenerationDto;
import com.suncontrol.core.dto.log.component.GenerateValueDto;

import java.time.LocalDateTime;

/// 발전기록 생성 Util인터페이스
public interface GenerateUtil {
    /// 발전기록 생성
    public GenerateValueDto generateEnergy(
            LocalDateTime baseTime,
            InverterGenerationDto inv,
            GenerateCalcBase base,
            GenerateValueDto dto);
}
