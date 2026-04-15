package com.suncontrol.core.dto.report;

import com.suncontrol.common.dto.generate.GenerateValueDto;
import com.suncontrol.core.dto.component.GenerationValuesDto;
import com.suncontrol.core.dto.component.StoppedDto;
import com.suncontrol.core.entity.report.MonthlyReport;
import com.suncontrol.core.util.SafeDivider;
import com.suncontrol.core.util.TimeTruncater;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyReportDto {
    private Long inverterId;
    private String baseMonth;
    private BigDecimal valueExpected; /// 기대값
    private BigDecimal valueActual; /// 실측값
    private BigDecimal valuePrevious; /// 전 실측값
    private BigDecimal performanceRatio; /// 실측값 / 기대값
    private BigDecimal expectedRatio; /// 기대값 / 인버터용량
    private BigDecimal capacityFactor; /// 실측값 / 인버터용량
    private BigDecimal accumEnergy; /// 인버터 계량기 수치
    private int stoppedTime; /// 가동정지 시간(초)
    private int incidentCount; /// 가동정지 회수

    public MonthlyReportDto(MonthlyReport entity) {
        this.inverterId = entity.getInverterId();
        this.baseMonth = entity.getBaseMonth();
        this.valueExpected = entity.getValueExpected();
        this.valueActual = entity.getValueActual();
        this.valuePrevious = entity.getValuePrevious();
        this.performanceRatio = entity.getPerformanceRatio();
        this.expectedRatio = entity.getExpectedRatio();
        this.capacityFactor = entity.getCapacityFactor();
        this.accumEnergy = entity.getAccumEnergy();
        this.stoppedTime = entity.getStoppedTime();
        this.incidentCount = entity.getIncidentCount();
    }

    public GenerationValuesDto getValuesDto() {
        GenerationValuesDto dto = new GenerationValuesDto();
        dto.setInverterId(inverterId);
        dto.setValueActual(valueActual);
        dto.setValueExpected(valueExpected);
        dto.setAccumEnergy(accumEnergy);

        return dto;
    }

    public LocalDate getBaseDate() {
        return YearMonth.parse(baseMonth).atDay(1);
    }

    public MonthlyReportDto(GenerationValuesDto dto, List<StoppedDto> stoppedDtoList, BigDecimal capacity) {
        this.inverterId = dto.getInverterId();
        this.baseMonth = TimeTruncater.getBaseMonth(dto.getBaseTime());
        this.valueExpected = dto.getValueExpected();
        this.valueActual = dto.getValueActual();
        this.valuePrevious = dto.getValuePrevious();
        this.performanceRatio = dto.getPerformanceRatio();
        this.accumEnergy = dto.getAccumEnergy();

        LocalDate date = dto.getBaseTime().toLocalDate();
        BigDecimal totalCap = capacity.multiply(BigDecimal.valueOf(24L * date.lengthOfMonth()));

        this.expectedRatio = SafeDivider.ratioDivide(valueExpected, totalCap);
        this.capacityFactor = SafeDivider.ratioDivide(valueActual, totalCap);

        this.stoppedTime = stoppedDtoList.stream()
                .mapToInt(StoppedDto::getStoppedTime)
                .sum();
        this.incidentCount = stoppedDtoList.stream()
                .mapToInt(StoppedDto::getIncidentCount)
                .sum();
    }
}
