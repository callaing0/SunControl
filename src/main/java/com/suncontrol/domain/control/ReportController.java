package com.suncontrol.domain.control;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.service.report.DailyReportService;
import com.suncontrol.domain.dto.IncidentReportDto;
import com.suncontrol.domain.dto.MonDailyReportDto;
import com.suncontrol.domain.dto.ReportDto;
import com.suncontrol.domain.dto.ReportPdfDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final DailyReportService dailyReportService;

    @GetMapping
    public String getMonthlyReport(Model model){
        model.addAttribute("reportDto",new ReportDto());

        List<DailyReportDto> dailyReportDtoList=dailyReportService.findAllByDateBetween(LocalDate.of(2026,4,1),LocalDate.of(2026,4,30),0);

        model.addAttribute("dailyReportDtoList",dailyReportDtoList);

        return "report";

    }

    @GetMapping("/pdfview")
    public String getPdfReport(Model model){
        model.addAttribute("reportPdfDto",new ReportPdfDto());
        model.addAttribute("incidentReportDto",new IncidentReportDto());

        return "reportPdf";
    }



}
