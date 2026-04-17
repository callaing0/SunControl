package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReportDto {
    private Long plantId;
    private BigDecimal valueActual; // 월 누적 발전량
    private BigDecimal  increaseRate; // 전월 대비 증감률
    private BigDecimal  performanceRatio; // 기대 달성도
    private Integer incidentCount; // 장애 발생건수
    private Double stoppedTime; // 월가동 중지시간

}
