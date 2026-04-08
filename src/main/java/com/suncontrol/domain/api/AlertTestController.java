package com.suncontrol.domain.api;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.domain.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlertTestController {

    private final AlertService alertService;

    @GetMapping("/alerts")
    public String showAlerts(@RequestParam(value = "location", required = false) String location,
                             Model model) {

        List<AlertDTO> alerts = alertService.getAlertList(location);
        model.addAttribute("alerts", alerts);
        model.addAttribute("paramLocation", location);

        return "alerts";
    }
}