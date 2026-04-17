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


@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final MonthlyReportService monthlyReportService;
    private final DailyReportService dailyReportService;

    @GetMapping
    public String getMonthlyReport(Model model, HttpSession session){

        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");


        List<DailyReportDto> dailyReportDtoList = dailyReportService.findByPlantIdDateBetween(selectedPlantId,LocalDate.of(2026,4,1), LocalDate.of(2026,4,30),0);
        model.addAttribute("dailyReportDtoList",dailyReportDtoList);

        ReportDto reportDto = dailyReportService.makeMonthlyReportDto(selectedPlantId,dailyReportDtoList);

        model.addAttribute("reportDto",reportDto);
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
