package com.suncontrol.common.util;

import com.suncontrol.common.dto.generate.GenerateCalcBase;
import com.suncontrol.common.dto.generate.GenerateDataContext;
import com.suncontrol.common.dto.generate.InverterGenerationDto;
import com.suncontrol.common.dto.generate.GenerateValueDto;
import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.constant.util.StaticValues;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/// 더미 발전기록 생성 인터페이스 구현체
@Component("DUMMY_ACTUAL")
public class DummyActualUtil implements GenerateUtil {

    @Override
    public GenerateDataContext generateEnergy(GenerateDataContext context) {
        GenerateValueDto dto = context.getDto();
        InverterGenerationDto inv = context.getInv();
        double rate = Math.random();
        if(!inv.getStatus().equals(DeviceStatus.INVERTER_NORMAL)) {
            if(rate < StaticValues.NORMALIZE_RATE) {
                inv.setStatus(DeviceStatus.INVERTER_NORMAL);
            } else {
                context.setDto(dto.zeroActual());
                context.setInv(inv);
                return context;
            }
        } else if (rate < StaticValues.DISABLE_RATE) {
            double occurredTime = Math.random();
            dto.setValueActual(getValueActual(dto.getValueExpected(), occurredTime));
            inv.setStatus(DeviceStatus.INVERTER_INTERNAL_ERROR);
            context.setDto(dto);
            context.setInv(inv);
            return context;
        }
        /// 설비용량 넘어서는 발전량은 오케스트레이터에서 처리할 예정이니
        /// 걱정하지 말고 생성만 할것!
        double noiseFactor =
                StaticValues.NOISE_BASE + Math.random() * StaticValues.NOISE_SCALE;
        dto.setValueActual(getValueActual(dto.getValueExpected(), noiseFactor));
        context.setDto(dto);

        return context;
    }

    private BigDecimal getValueActual(BigDecimal exp, double multiplyFactor) {
        return exp.multiply(BigDecimal.valueOf(multiplyFactor));
    }
}
