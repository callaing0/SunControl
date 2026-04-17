package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportPdfDto {
    private LocalDateTime createdDate=LocalDateTime.now();   // 리포트 생성일시
    private LocalDate analysisPeriod;    // 분석기간
    private LocalDate analysisPeriod2;

    private String plantName;            // 발전소명
    private String plantAddress;         // 발전소 주소
    private BigDecimal capacity;            // 설비용량
    private String name;                 // 담당자명

    private BigDecimal valueActual;          // 당월 발전량
    private BigDecimal increaseRate;         // 전월 대비 증감률
    private BigDecimal performanceRatio;     // 목표 달성률
    private int incidentCount;       // 장애 발생 건수
    private int stoppedTime;         // 월 가동 정지 시간
}
