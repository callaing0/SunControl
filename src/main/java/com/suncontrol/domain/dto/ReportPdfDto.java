package com.suncontrol.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReportPdfDto {
    private LocalDateTime createdDate=LocalDateTime.of(2026,03,01, 15,00,00); // 리포트 생성일시
    private LocalDate anlaysisPeriod=LocalDate.of(2026,03,31); //분석기간
    private LocalDate date=LocalDate.of(2026,03,15);
    private String plantName="대전 태양광 발전소 B";// 발전소명
    private String plantAddress="대전 광역시 중구"; // 발전소 주소
    private Integer capacity=100;// 설비용량
    private String name="김철수"; // 담당자명
    private Double valueActual=100.5;// 당월 발전량
    private Double increaseRate=8.3; // 전월 대비 증감률
    private Double performanceRatio=72.5; // 목표 달성률
    private Integer incidentCount=1;  // 장애 발생 건수
    private Integer stoppedTime=3; // 월 가동 정지 시간
}
