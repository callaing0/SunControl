package com.suncontrol.common.util;

import com.suncontrol.core.dto.log.GenerationLogDto;
import org.springframework.stereotype.Component;

/// 가상 일사량 및 기대값 생성 인터페이스 구현체
@Component("VIRTUAL_EXPECTED")
public class VirtualExpectedUtil implements GenerateUtil{

    @Override
    public GenerationLogDto generateEnergy() {
        // todo
        return null;
    }
}
