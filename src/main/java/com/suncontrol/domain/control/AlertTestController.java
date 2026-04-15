package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.service.AlertProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlertTestController {

    private final AlertProcessService alertProcessService;

    @GetMapping("/alerts")
    public String listAlerts(Model model) {
        List<AlertResponseDTO> alerts = alertProcessService.findAll();
        model.addAttribute("alerts", alerts);
        return "alerts";
    }

    // 알림 테스트용 API (상태 변경 없이 알림만 쏨)
    @PostMapping("/api/alerts/test-notify/{id}")
    @ResponseBody
    public String testNotify(@PathVariable("id") Long id) {
        alertProcessService.sendSimpleNotification(id);
        return "알림 전송 시도 완료";
    }
}