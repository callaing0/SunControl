package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.IncidentReportDto;
import com.suncontrol.domain.dto.MonDailyReportDto;
import com.suncontrol.domain.dto.ReportDto;
import com.suncontrol.domain.dto.ReportPdfDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/report")
public class ReportController {
    @GetMapping
    public String getMonthlyReport(Model model){
        model.addAttribute("reportDto",new ReportDto());
        model.addAttribute("monDailyReportDto",new MonDailyReportDto());

        return "report";

    }

    @GetMapping("/pdfview")
    public String getPdfReport(Model model){
        model.addAttribute("reportPdfDto",new ReportPdfDto());
        model.addAttribute("incidentReportDto",new IncidentReportDto());

        return "reportPdf";
    }



}
