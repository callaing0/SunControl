package com.suncontrol.core.service.report;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.dto.report.MonthlyReportDto;
import com.suncontrol.core.entity.Member;
import com.suncontrol.core.entity.asset.Plant;
import com.suncontrol.core.entity.report.MonthlyReport;
import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.repository.asset.InverterRepository;
import com.suncontrol.core.repository.asset.PlantRepository;
import com.suncontrol.core.repository.report.MemberRepository;
import com.suncontrol.core.repository.report.MonthlyReportRepository;
import com.suncontrol.core.repository.report.plantSelectionRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import com.suncontrol.core.util.TimeTruncater;
import com.suncontrol.domain.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonthlyReportService {
    private final MonthlyReportRepository repository;
    private final PlantRepository plantRepository;
    private final MemberRepository memberRepository;
    private final InverterRepository inverterRepository;


    public void saveAll(List<MonthlyReportDto> dtoList) {
        if(dtoList==null || dtoList.isEmpty()){
            log.warn("no monthly report generated");
            return;
        }
        int result = repository.saveAll(DataCollectorsUtil.toDataList(dtoList, MonthlyReport::new));
        log.info("save monthly report result: {}", result);
    }

    public List<MonthlyReportDto> findAllByMonthBetween(LocalDate start, LocalDate end) {
        String startMonth = TimeTruncater.getBaseMonth(start);
        String endMonth = TimeTruncater.getBaseMonth(end);

        return entityToDto(repository.findAllByMonthBetween(startMonth, endMonth));
    }

    public List<MonthlyReportDto> findAllLatestByInverter() {

        return entityToDto(repository.findAllLatestByInverter());
    }

    private List<MonthlyReportDto> entityToDto(List<MonthlyReport> entities) {
        if(entities==null || entities.isEmpty()){
            log.warn("no monthly report found");
            return Collections.emptyList();
        }

        return DataCollectorsUtil.toDataList(entities, MonthlyReportDto::new);
    }

    public ReportPdfDto getPlantInfo(Long selectedPlantId, String userName , YearMonth  month) {
        Member member = memberRepository.findByUserId(userName);
        PlantInfoView plantInfoView = plantRepository.findPlantInfoViewById(selectedPlantId);
        ReportPdfDto reportPdfDto = new ReportPdfDto();
        reportPdfDto.setName(member.getName());
        reportPdfDto.setPlantName(plantInfoView.getName());
        reportPdfDto.setCapacity(plantInfoView.getCapacitySum());
        reportPdfDto.setPlantAddress(plantInfoView.getAddress());

        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        reportPdfDto.setAnalysisPeriod( startDate  );
        reportPdfDto.setAnalysisPeriod2( endDate);

        Long inverterId= inverterRepository.findAllByPlantId(selectedPlantId).get(0).getId();

        MonthlyReport monthlyReport =  repository.findByInverterIdAndBaseMonth(inverterId, month);
        reportPdfDto.setValueActual(monthlyReport.getValueActual());
        reportPdfDto.setIncidentCount(monthlyReport.getIncidentCount());
        reportPdfDto.setStoppedTime(monthlyReport.getStoppedTime()/3600);
        reportPdfDto.setPerformanceRatio(monthlyReport.getPerformanceRatio());
        BigDecimal valueActual = monthlyReport.getValueActual();
        BigDecimal valuePrevious = monthlyReport.getValuePrevious();
        BigDecimal increaseRate = !valuePrevious.equals(BigDecimal.ZERO) ?
                new BigDecimal("100.0") : valueActual.subtract(valuePrevious).divide(valuePrevious, 4, RoundingMode.HALF_EVEN).movePointRight(2);
        log.info("increase rate: {}, monthlyReport : {}", increaseRate, monthlyReport);
        reportPdfDto.setIncreaseRate(increaseRate);

        return reportPdfDto;
    }

    ///전월 대비 차트
    public MonthlyCompareChartDto getMonthlyCompareChart(Long plantId, YearMonth month) {
        MonthlyCompareChartDto dto = new MonthlyCompareChartDto();
        Long inverterId= inverterRepository.findAllByPlantId(plantId).get(0).getId();

        MonthlyReport current = repository
                .findByInverterIdAndBaseMonth(inverterId, month);
        MonthlyReport previous = repository.findByInverterIdAndBaseMonth(inverterId, month.minusMonths(1));

        BigDecimal currentActual = current != null && current.getValueActual() != null
                ? current.getValueActual() : BigDecimal.ZERO;

        BigDecimal previousActual = previous != null && previous.getValueActual() != null
                ? previous.getValueActual() : BigDecimal.ZERO;

        BigDecimal targetActual = current != null && current.getValueExpected() != null
                ? current.getValueExpected() : BigDecimal.ZERO;

        BigDecimal increaseRate = BigDecimal.ZERO;
        if (previousActual.compareTo(BigDecimal.ZERO) > 0) {
            increaseRate = currentActual.subtract(previousActual)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(previousActual, 1, java.math.RoundingMode.HALF_UP);
        }

        dto.setCurrentActual(currentActual);
        dto.setPreviousActual(previousActual);
        dto.setTargetActual(targetActual);
        dto.setIncreaseRate(increaseRate);

        return dto;
    }

    /// 모표 달성률 게이지
    public PerformanceGaugeDto getPerformanceGauge(Long plantId, YearMonth month) {
        PerformanceGaugeDto dto = new PerformanceGaugeDto();
        Long inverterId= inverterRepository.findAllByPlantId(plantId).get(0).getId();
        MonthlyReport current = repository.findByInverterIdAndBaseMonth(inverterId, month);

        BigDecimal actualValue = current != null && current.getValueActual() != null
                ? current.getValueActual() : BigDecimal.ZERO;

        BigDecimal targetValue = current != null && current.getValueExpected() != null
                ? current.getValueExpected() : BigDecimal.ZERO;

        BigDecimal remainValue = targetValue.subtract(actualValue);
        if (remainValue.compareTo(BigDecimal.ZERO) < 0) {
            remainValue = BigDecimal.ZERO;
        }

        BigDecimal ratio = BigDecimal.ZERO;
        if (targetValue.compareTo(BigDecimal.ZERO) > 0) {
            ratio = actualValue.multiply(BigDecimal.valueOf(100))
                    .divide(targetValue, 1, java.math.RoundingMode.HALF_UP);
        }

        dto.setActualValue(actualValue);
        dto.setRemainValue(remainValue);
        dto.setTargetValue(targetValue);
        dto.setPerformanceRatio(ratio);

        return dto;
    }

    /// 월 가동 시간 게이지
    public RuntimeGaugeDto getRuntimeGauge(Long plantId, YearMonth month) {
        RuntimeGaugeDto dto = new RuntimeGaugeDto();
        Long inverterId= inverterRepository.findAllByPlantId(plantId).get(0).getId();
        MonthlyReport current = repository.findByInverterIdAndBaseMonth(inverterId, month);

        int targetHours = month.lengthOfMonth() * 10;

        int stoppedTimeSeconds = current != null ? current.getStoppedTime() : 0;
        int stoppedHours = (int) Math.round(stoppedTimeSeconds / 3600.0);

        int runHours = targetHours - stoppedHours;
        if (runHours < 0) {
            runHours = 0;
        }

        int remainHours = targetHours - runHours;
        int runRatio = targetHours == 0 ? 0 : (int) Math.round((runHours * 100.0) / targetHours);

        dto.setRunHours(runHours);
        dto.setRemainHours(remainHours);
        dto.setTargetHours(targetHours);
        dto.setRunRatio(runRatio);

        return dto;
    }
}
