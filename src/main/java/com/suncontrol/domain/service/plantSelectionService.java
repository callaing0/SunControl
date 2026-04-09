package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.PlantSelectDto;

import java.util.List;

public interface plantSelectionService {

    List<PlantSelectDto> getPlantList(Long memberId);

    PlantSelectDto getSelectedPlant(Long memberId, Long selectedPlantId);

    Long resolveSelectedPlantId(Long memberId, Long selectedPlantId);
}
