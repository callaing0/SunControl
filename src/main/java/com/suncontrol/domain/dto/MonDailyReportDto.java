package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MonDailyReportDto {
    private LocalDate monthDay=LocalDate.of(2026,03,12); // 날짜
    private Double valueActual=155.5;// 발전량
    private Double changeRate=5.2; // 전일대비
    private Double performanceRatio=60.2; // 목표 달성도
    private Integer incidentCount=2; // 장애 발생
    private Integer stoppedTime=5; // 일 가동 정지 시간



}
