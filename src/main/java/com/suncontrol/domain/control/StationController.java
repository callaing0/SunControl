package com.suncontrol.domain.control;

import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;
import com.suncontrol.domain.form.StationSearchForm;
import com.suncontrol.domain.service.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping("/stations")
    public String getStationsPage(@Valid StationSearchForm stationSearchForm,
                                  BindingResult bindingResult,
                                  Model model) {

        String region = "all";
        if (!bindingResult.hasErrors()) {
            region = stationSearchForm.normalizedRegion();
        }

        List<String> regionList = stationService.getRegionList();
        List<PlantInfoView> plantList = stationService.getPlantList(region);
        List<StationHourlyGenerationDto> hourlyGenerationList =
                stationService.getHourlyGenerationList(region);

        List<String> hourlyLabels = new ArrayList<>();
        List<BigDecimal> hourlyData = new ArrayList<>();

        for (StationHourlyGenerationDto item : hourlyGenerationList) {
            hourlyLabels.add(item.getHourLabel());
            hourlyData.add(
                    item.getTotalGeneration() != null
                            ? item.getTotalGeneration()
                            : BigDecimal.ZERO
            );
        }

        model.addAttribute("selectedRegion", region);
        model.addAttribute("regionList", regionList);
        model.addAttribute("plantList", plantList);
        model.addAttribute("hourlyLabels", hourlyLabels);
        model.addAttribute("hourlyData", hourlyData);

        return "stations";
    }
}