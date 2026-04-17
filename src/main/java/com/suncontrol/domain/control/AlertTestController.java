package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.service.AlertProcessService;
import com.suncontrol.domain.service.AlertSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlertTestController {

    private final AlertProcessService alertProcessService;
    private final AlertSaveService alertSaveService;

    @GetMapping("/alerts")
    public String listAlerts(Model model) {
        List<AlertResponseDTO> alerts = alertProcessService.findAll();
        model.addAttribute("alerts", alerts);
        return "alerts";
    }

    @PostMapping("/alerts/{id}/status")
    public String changeAlertStatus(@PathVariable("id") Long id) {
        alertSaveService.changeAlertStatus(id);
        return "redirect:/alerts";
    }

    @PostMapping("/api/alerts/test-notify/{id}")
    @ResponseBody
    public String testNotify(@PathVariable("id") Long id) {
        alertProcessService.sendSimpleNotification(id);
        return "알림 전송 시도 완료";
    }
}