package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DailyGenerationChartDto {
    private List<String> labels = new ArrayList<>();   // "1", "2", "3" ...
    private List<Double> values = new ArrayList<>();   // 일별 발전량
    private Double averageValue = 0.0;                 // 월 평균 발전량
    private Double maxValue = 0.0;                     // 최대 발전량
    private Integer maxDay = 0;                        // 최대 발전일
}
