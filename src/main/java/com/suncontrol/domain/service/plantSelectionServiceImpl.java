package com.suncontrol.domain.service;

import com.suncontrol.core.repository.report.plantSelectionRepository;
import com.suncontrol.domain.dto.PlantSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class plantSelectionServiceImpl implements plantSelectionService {

    private final plantSelectionRepository plantSelectionRepository;

    @Override
    public List<PlantSelectDto> getPlantList(Long memberId) {
        return plantSelectionRepository.selectPlantListByMemberId(memberId);
    }

    @Override
    public PlantSelectDto getSelectedPlant(Long memberId, Long selectedPlantId) {
        List<PlantSelectDto> plantList = getPlantList(memberId);

        if (plantList == null || plantList.isEmpty()) {
            return null;
        }

        if (selectedPlantId != null) {
            PlantSelectDto selectedPlant = plantSelectionRepository.selectPlantById(memberId, selectedPlantId);
            if (selectedPlant != null) {
                return selectedPlant;
            }
        }

        return plantList.get(0);
    }

    @Override
    public Long resolveSelectedPlantId(Long memberId, Long selectedPlantId) {

        if (memberId == null || selectedPlantId == null) {
            return null;
        }

        PlantSelectDto plant = plantSelectionRepository.selectPlantById(memberId, selectedPlantId);

        return (plant != null) ? plant.getId() : null;
    }
}