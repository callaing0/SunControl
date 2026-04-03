package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.statsChartDto;
import com.suncontrol.domain.service.statsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class statsController {

    private final statsService statsService;

    @GetMapping("/stats")
    public String stats(Model model) {
        Long memberId = 1L;
        String selectedDate = LocalDate.now().toString();

        List<statsChartDto> generationTrend = statsService.getGenerationTrend(memberId, selectedDate);
        List<statsChartDto> weatherEfficiency = statsService.getWeatherEfficiency(memberId, selectedDate);

        model.addAttribute("statsSummary", statsService.getStatsSummary(memberId, selectedDate));
        model.addAttribute("generationTrend", generationTrend);
        model.addAttribute("weatherEfficiency", weatherEfficiency);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("menu", "stats");

        return "stats";
    }
}
