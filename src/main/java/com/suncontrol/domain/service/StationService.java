package com.suncontrol.domain.service;

import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;

import java.util.List;

public interface StationService {

    List<String> getRegionList(Long memberId);

    List<PlantInfoView> getPlantList(Long memberId, String region);

    List<StationHourlyGenerationDto> getHourlyGenerationList(Long memberId, String region);
}