package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.ReportDto;
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


        return "report";

    }

    @GetMapping("/pdfview")
    public String getPdfReport(){
        return "reportPdf";
    }



}
