package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private Long plantId;
    private Double valueActual=8.342; // 월 누적 발전량
    private Double increaseRate=3.4; // 전월 대비 증감률
    private Double performanceRatio=78.4; // 기대 달성도
    private Integer incidentCount=4; // 장애 발생건수
    private Double stoppedTime=3.5; // 월가동 중지시간

}
