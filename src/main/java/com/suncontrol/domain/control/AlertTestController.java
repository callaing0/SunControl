package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.service.AlertListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlertTestController {

    private final AlertListService alertListService;

    @GetMapping("/alerts")
    public String showAlerts(@RequestParam(value = "location", required = false) String location, Model model) {
        List<AlertResponseDTO> alerts = alertListService.getAlertList(location);
        model.addAttribute("alerts", alerts);
        return "alerts";
    }
}