package com.suncontrol.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class dashboardSummaryDto {

    private Long plantId;

    private BigDecimal currentPower; // 현재 출력
    private BigDecimal dailyAccumulation; // 금일 누적 발전량
    private BigDecimal efficiency; // 효율

    private String plantName;
    private String location; // 발전소 위치
    private String sunTime; // 일출 / 일몰 시간
    private BigDecimal insolation; // 금일 평균 일사량
    private String weatherStatus; // 기온 표시값

    private BigDecimal totalProfit; // 예상 수익
    private BigDecimal predGen; // 예상 발전량
    private BigDecimal unitPrice; // 판매 단가

    private BigDecimal co2Reduction; // CO2 절감량
    private Integer treeCount; // 나무 환산

    private List<String> chartLabels; // 차트 X축
    private List<BigDecimal> powerList; // 시간대별 발전량 리스트
    private List<BigDecimal> insolationList; // 시간대별 일사량 리스트
}