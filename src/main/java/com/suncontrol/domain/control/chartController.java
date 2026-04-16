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

        if (resolvedPlantId == null) {
            model.addAttribute("statsSummary", List.of());
            model.addAttribute("generationTrend", List.of());
            model.addAttribute("weatherEfficiency", List.of());
            model.addAttribute("selectedDate", selectedDate);
            model.addAttribute("menu", "stats");
            return "chart";
        }

        session.setAttribute("selectedPlantId", resolvedPlantId);

        model.addAttribute("statsSummary", chartService.getStatsSummary(resolvedPlantId, selectedDate));
        model.addAttribute("generationTrend", chartService.getGenerationTrend(resolvedPlantId, selectedDate));
        model.addAttribute("weatherEfficiency", chartService.getWeatherEfficiency(resolvedPlantId, selectedDate));
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("menu", "stats");

        return "chart";
    }

    @GetMapping("/chartMonthly")
    public String monthlyChart(
            @RequestParam(value = "selectedMonth", required = false) String selectedMonth,
            Model model,
            HttpSession session
    ) {
        Long memberId = (Long) session.getAttribute("memberId");

        if (selectedMonth == null || selectedMonth.isBlank()) {
            selectedMonth = LocalDate.now().minusMonths(1).toString().substring(0, 7);
        }

        if (memberId == null) {
            model.addAttribute("statsSummary", List.of());
            model.addAttribute("generationTrend", List.of());
            model.addAttribute("weatherDistribution", List.of());
            model.addAttribute("selectedMonth", selectedMonth);
            model.addAttribute("menu", "stats");
            return "chartMonthly";
        }

        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");
        Long resolvedPlantId = plantSelectionService.resolveSelectedPlantId(memberId, selectedPlantId);

        if (resolvedPlantId == null) {
            model.addAttribute("statsSummary", List.of());
            model.addAttribute("generationTrend", List.of());
            model.addAttribute("weatherDistribution", List.of());
            model.addAttribute("selectedMonth", selectedMonth);
            model.addAttribute("menu", "stats");
            return "chartMonthly";
        }

        session.setAttribute("selectedPlantId", resolvedPlantId);

        model.addAttribute("statsSummary", chartService.getMonthlySummary(resolvedPlantId, selectedMonth));
        model.addAttribute("generationTrend", chartService.getMonthlyTrend(resolvedPlantId));
        model.addAttribute("weatherDistribution", chartService.getMonthlyWeather(resolvedPlantId, selectedMonth));
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("menu", "stats");

        return "chartMonthly";
    }
}