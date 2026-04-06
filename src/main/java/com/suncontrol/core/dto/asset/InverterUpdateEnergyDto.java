package com.suncontrol.core.dto.asset;

import com.suncontrol.core.dto.asset.component.InverterBaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class InverterUpdateEnergyDto extends InverterBaseDto {
    // todo
    /// 인버터의 누적발전량 변경
}
