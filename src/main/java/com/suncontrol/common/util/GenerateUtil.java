package com.suncontrol.common.util;

import com.suncontrol.core.dto.log.GenerationLogDto;

/// 발전기록 생성 Util인터페이스
public interface GenerateUtil {
    /// 발전기록 생성
    public GenerationLogDto generateEnergy();
}
