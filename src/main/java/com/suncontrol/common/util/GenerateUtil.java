package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.GenerateDataContext;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.common.dto.generate.GenerateValueDto;

import java.time.LocalDateTime;

/// 발전기록 생성 Util인터페이스
public interface GenerateUtil {
    /// 발전기록 생성
    public GenerateDataContext generateEnergy(GenerateDataContext context);
}
