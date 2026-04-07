package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.service.chartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class chartController {

    private final chartService chartService;

    @GetMapping("/chart")
    public String chart(Model model) {
        Long memberId = 1L;
        LocalDate selectedDate = LocalDate.now();

        List<chartDto> generationTrend = chartService.getGenerationTrend(memberId, selectedDate);
        List<chartDto> weatherEfficiency = chartService.getWeatherEfficiency(memberId, selectedDate);

        model.addAttribute("statsSummary", chartService.getStatsSummary(memberId, selectedDate));
        model.addAttribute("generationTrend", generationTrend);
        model.addAttribute("weatherEfficiency", weatherEfficiency);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("menu", "stats");

        return "chart";
    }
}