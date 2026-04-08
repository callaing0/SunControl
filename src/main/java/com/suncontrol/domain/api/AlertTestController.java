package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.domain.service.AlertListService; // 전용 서비스 사용
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
    public String showAlerts(@RequestParam(value = "location", required = false) String location,
                             Model model) {
        // 이력 조회 중심의 로직 수행
        List<AlertDTO> alerts = alertListService.getAlertList(location);
        model.addAttribute("alerts", alerts);
        model.addAttribute("paramLocation", location);

        return "alerts";
    }
}