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

        /**
         * 🔥 테스트용 사용자 ID (로그인 제거)
         */
        Long memberId = 1L;

        /**
         * region 파라미터 처리
         */
        String region = "all";
        if (!bindingResult.hasErrors()) {
            region = stationSearchForm.normalizedRegion();
        }

        /**
         * 데이터 조회
         */
        List<String> regionList = stationService.getRegionList(memberId);
        List<PlantInfoView> plantList = stationService.getPlantList(memberId, region);
        List<StationHourlyGenerationDto> hourlyGenerationList =
                stationService.getHourlyGenerationList(memberId, region);

        /**
         * Chart.js용 데이터 가공
         */
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

        /**
         * View 전달
         */
        model.addAttribute("selectedRegion", region);
        model.addAttribute("regionList", regionList);
        model.addAttribute("plantList", plantList);
        model.addAttribute("hourlyLabels", hourlyLabels);
        model.addAttribute("hourlyData", hourlyData);

        /**
         * 템플릿 반환
         */
        return "stations";
    }
}