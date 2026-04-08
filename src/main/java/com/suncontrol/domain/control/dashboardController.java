package com.suncontrol.domain.control;

import com.suncontrol.domain.service.dashboardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class dashboardController {

    private final dashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Long memberId = 1L;
        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");

        model.addAttribute("summary", dashboardService.getDashboardSummary(memberId, selectedPlantId));
        model.addAttribute("inverters", dashboardService.getInverters(memberId, selectedPlantId));
        model.addAttribute("menu", "dashboard");

        return "dashboard";
    }
}