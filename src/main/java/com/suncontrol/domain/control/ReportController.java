package com.suncontrol.domain.control;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.dto.report.MonthlyReportDto;
import com.suncontrol.core.service.report.DailyReportService;
import com.suncontrol.core.service.report.MonthlyReportService;
import com.suncontrol.domain.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final MonthlyReportService monthlyReportService;
    private final DailyReportService dailyReportService;

    @GetMapping
    public String getMonthlyReport(@RequestParam Optional<YearMonth> month, Model model, HttpSession session,Principal principal){
        String userName = principal.getName();
        YearMonth selectMonth = month.orElseGet(YearMonth::now);

        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");

        LocalDate startDate = selectMonth.atDay(1);
        LocalDate endDate = selectMonth.atEndOfMonth();

        List<DailyReportDto> dailyReportDtoList = dailyReportService.findByPlantIdDateBetween(selectedPlantId,startDate, endDate,0);
        model.addAttribute("dailyReportDtoList",dailyReportDtoList);

        ReportPdfDto reportPdfDto = monthlyReportService.getPlantInfo(selectedPlantId, userName,selectMonth);

        model.addAttribute("reportDto",reportPdfDto);
        model.addAttribute("nowMonth",selectMonth);
        return "report";

    }

    @GetMapping("/pdfview")
    public String getPdfReport(@RequestParam("month") YearMonth month, Model model, HttpSession session, Principal principal){

        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");
        String userName = principal.getName();

        ReportPdfDto reportPdfDto = monthlyReportService.getPlantInfo(selectedPlantId, userName,month);

        DailyGenerationChartDto dailyChartDto =
                dailyReportService.getDailyChart(selectedPlantId, month);

        MonthlyCompareChartDto monthlyCompareChartDto =
                monthlyReportService.getMonthlyCompareChart(selectedPlantId, month);

        PerformanceGaugeDto performanceGaugeDto =
                monthlyReportService.getPerformanceGauge(selectedPlantId, month);

        RuntimeGaugeDto runtimeGaugeDto =
                monthlyReportService.getRuntimeGauge(selectedPlantId, month);

        model.addAttribute("reportPdfDto",reportPdfDto);
        model.addAttribute("dailyChartDto", dailyChartDto);
        model.addAttribute("monthlyCompareChartDto", monthlyCompareChartDto);
        model.addAttribute("performanceGaugeDto", performanceGaugeDto);
        model.addAttribute("runtimeGaugeDto", runtimeGaugeDto);

        model.addAttribute("incidentReportDto",new IncidentReportDto());

        return "reportPdf";
    }



}
