package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuntimeGaugeDto {  //월 가동 시간 게이지 DTO
    private Integer runHours;       // 가동 시간
    private Integer remainHours;    // 잔여 시간
    private Integer targetHours;    // 목표 시간
    private Integer runRatio;       // 가동률 %
}
