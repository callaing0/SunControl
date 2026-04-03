package com.suncontrol.common.util;

import com.suncontrol.core.dto.log.GenerationLogDto;
import org.springframework.stereotype.Component;

/// 실제 기상모델 적용 기대값 생성기
@Component("REAL_EXPECTED")
public class ExpectedByWeatherUtil implements GenerateUtil{
    @Override
    public GenerationLogDto generateEnergy() {
        // todo
        return null;
    }
}
