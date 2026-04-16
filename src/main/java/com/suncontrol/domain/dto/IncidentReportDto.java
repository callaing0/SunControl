package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncidentReportDto {
    private LocalDateTime detectTime=LocalDateTime.of(2026,3,1,8,10); // 발생 일시
    private String InverterId="INV-A1"; //인버터명
    private String type="먼지 경고"; // 장애 유형
    private String statusCode="완료"; // 상태
    private String Comments="정상 가동중"; // 비고


}
