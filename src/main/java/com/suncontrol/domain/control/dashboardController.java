package com.suncontrol.domain.control;

import com.suncontrol.domain.service.dashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class dashboardController {

    private final dashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long memberId = 1L;

        model.addAttribute("summary", dashboardService.getDashboardSummary(memberId));
        model.addAttribute("inverters", dashboardService.getInverters(memberId));
        model.addAttribute("menu", "dashboard");

        return "dashboard";
    }
}