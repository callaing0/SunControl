package com.suncontrol.core.dto.asset;

import com.suncontrol.core.dto.asset.component.InverterBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InverterCapSurplusDto extends InverterBaseDto {
    /// 패널등록 Form 에서 인버터 설계용량
    /// 수정내용만 추출, InverterBaseDto상속
    private BigDecimal measuredCapSurplus;
}
