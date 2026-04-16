package com.suncontrol.domain.control;

import com.suncontrol.domain.service.dashboardService;
import com.suncontrol.domain.service.plantSelectionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class dashboardController {

    private final dashboardService dashboardService;
    private final plantSelectionService plantSelectionService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");

        if (memberId == null) {
            model.addAttribute("summary", null);
            model.addAttribute("inverters", java.util.Collections.emptyList());
            model.addAttribute("menu", "dashboard");
            return "dashboard";
        }

        Long resolvedPlantId = plantSelectionService.resolveSelectedPlantId(memberId, selectedPlantId);

        if (resolvedPlantId != null) {
            session.setAttribute("selectedPlantId", resolvedPlantId);
        }

        model.addAttribute("summary", dashboardService.getDashboardSummary(memberId, resolvedPlantId));
        model.addAttribute("inverters", dashboardService.getInverters(memberId, resolvedPlantId));
        model.addAttribute("menu", "dashboard");
        model.addAttribute("lastUpdateTime", dashboardService.getLastUpdateTime());

        return "dashboard";
    }
}