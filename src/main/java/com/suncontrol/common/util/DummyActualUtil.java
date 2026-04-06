package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.common.dto.generate.GenerateValueDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        final double DISABLE_RATE = 0.05; /// TODO : 나중에 시스템 변수로 이동
        double rate = Math.random();
        if(rate < DISABLE_RATE) {
            return dto.zeroActual();
        }
        /// 설비용량 넘어서는 발전량은 오케스트레이터에서 처리할 예정이니
        /// 걱정하지 말고 생성만 할것!
        final double NOISE_SCALE = 0.05;
        final double NOISE_BASE = 0.97;
        double noiseFactor = NOISE_BASE + Math.random() * NOISE_SCALE;
        dto.setValueActual(getValueActual(dto.getValueExpected(), noiseFactor));

        return dto;
    }

    private BigDecimal getValueActual(BigDecimal exp, double noise) {
        return exp.multiply(BigDecimal.valueOf(noise));
    }
}
