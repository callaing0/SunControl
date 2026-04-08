package com.suncontrol.domain.api;

import com.suncontrol.domain.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlertTestController {

    private final AlertService alertService;

    @GetMapping("/alerts")
    public String showAlertHistory(Model model) {
        // HTML의 th:each="alert : ${alerts}"와 이름 맞춤
        model.addAttribute("alerts", alertService.getAlertList());
        return "alertHistory";
    }
}