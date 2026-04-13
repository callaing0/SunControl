package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.service.chartService;
import com.suncontrol.domain.service.plantSelectionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class chartController {

    private final chartService chartService;
    private final plantSelectionService plantSelectionService;

    @GetMapping("/chart")
    public String chart(
            @RequestParam(value = "selectedDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate,
            Model model,
            HttpSession session
    ) {
        Long memberId = (Long) session.getAttribute("memberId");
        System.out.println("memberId = " + memberId);

        if (selectedDate == null) {
            selectedDate = LocalDate.now();
        }

        if (memberId == null) {
            model.addAttribute("statsSummary", List.of());
            model.addAttribute("generationTrend", List.of());
            model.addAttribute("weatherEfficiency", List.of());
            model.addAttribute("selectedDate", selectedDate);
            model.addAttribute("menu", "stats");
            return "chart";
        }

        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");
        Long resolvedPlantId = plantSelectionService.resolveSelectedPlantId(memberId, selectedPlantId);
        System.out.println("resolvedPlantId = " + resolvedPlantId);

        if (resolvedPlantId == null) {
            model.addAttribute("statsSummary", List.of());
            model.addAttribute("generationTrend", List.of());
            model.addAttribute("weatherEfficiency", List.of());
            model.addAttribute("selectedDate", selectedDate);
            model.addAttribute("menu", "stats");
            return "chart";
        }

        session.setAttribute("selectedPlantId", resolvedPlantId);

        List<chartDto> generationTrend = chartService.getGenerationTrend(resolvedPlantId, selectedDate);
        List<chartDto> weatherEfficiency = chartService.getWeatherEfficiency(resolvedPlantId, selectedDate);

        model.addAttribute("statsSummary", chartService.getStatsSummary(resolvedPlantId, selectedDate));
        model.addAttribute("generationTrend", generationTrend);
        model.addAttribute("weatherEfficiency", weatherEfficiency);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("menu", "stats");

        return "chart";
    }
}