package com.suncontrol.domain.control;

import com.suncontrol.core.entity.Member;
import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.repository.report.MemberRepository;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StationController {

    /**
     * 발전소 화면 서비스
     */
    private final StationService stationService;

    /**
     * 로그인 사용자 조회에 사용
     */
    private final MemberRepository memberRepository;

    /**
     * 지역 발전소 현황 페이지
     * URL: /stations
     * - 로그인 사용자 기준 발전소 목록 조회
     * - 지역 필터 적용
     * - 시간대별 발전량 차트 데이터 생성
     */
    @GetMapping("/stations")
    public String getStationsPage(@Valid StationSearchForm stationSearchForm,
                                  BindingResult bindingResult,
                                  Principal principal,
                                  Model model) {

        /**
         * 1. 로그인 사용자 검증
         */
        if (principal == null || principal.getName() == null || principal.getName().trim().isEmpty()) {
            return "redirect:/";
        }

        /**
         * 2. 사용자 정보 조회 (기존 MemberRepository 사용)
         */
        Member loginMember = memberRepository.findByUserId(principal.getName());

        if (loginMember == null || loginMember.getId() == null) {
            return "redirect:/";
        }

        /**
         * 3. region 파라미터 처리
         */
        String region = "all";
        if (!bindingResult.hasErrors()) {
            region = stationSearchForm.normalizedRegion();
        }

        /**
         * 4. 데이터 조회
         */
        List<String> regionList = stationService.getRegionList(loginMember.getId());

        List<PlantInfoView> plantList =
                stationService.getPlantList(loginMember.getId(), region);

        List<StationHourlyGenerationDto> hourlyGenerationList =
                stationService.getHourlyGenerationList(loginMember.getId(), region);

        /**
         * 5. Chart.js용 데이터 가공
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
         * 6. View 전달
         */
        model.addAttribute("selectedRegion", region);
        model.addAttribute("regionList", regionList);
        model.addAttribute("plantList", plantList);
        model.addAttribute("hourlyLabels", hourlyLabels);
        model.addAttribute("hourlyData", hourlyData);

        /**
         * 7. Thymeleaf 페이지 반환
         */
        return "station/stations";
    }
}