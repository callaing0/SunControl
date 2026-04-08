package com.suncontrol.domain.control;

import com.suncontrol.domain.dto.PlantSelectDto;
import com.suncontrol.domain.service.plantSelectionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class plantSelectionModelAdvice {

    private final plantSelectionService plantSelectionService;

    @ModelAttribute("plantList")
    public List<PlantSelectDto> plantList(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return Collections.emptyList();
        }
        return plantSelectionService.getPlantList(memberId);
    }

    @ModelAttribute("selectedPlant")
    public PlantSelectDto selectedPlant(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return null;
        }

        Long selectedPlantId = (Long) session.getAttribute("selectedPlantId");
        PlantSelectDto selectedPlant = plantSelectionService.getSelectedPlant(memberId, selectedPlantId);

        if (selectedPlant != null) {
            session.setAttribute("selectedPlantId", selectedPlant.getId());
        }

        return selectedPlant;
    }
}