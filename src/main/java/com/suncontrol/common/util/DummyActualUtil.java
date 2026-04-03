package com.suncontrol.common.util;

import com.suncontrol.core.dto.log.GenerationLogDto;
import org.springframework.stereotype.Component;

/// 더미 발전기록 생성 인터페이스 구현체
@Component("DUMMY_ACTUAL")
public class DummyActualUtil implements GenerateUtil {

    @Override
    public GenerationLogDto generateEnergy() {
        // todo
        return null;
    }
}
