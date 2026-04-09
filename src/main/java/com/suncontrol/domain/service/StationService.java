package com.suncontrol.domain.service;

import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;

import java.util.List;

public interface StationService {

    /**
     * 현재 로그인 사용자의 발전소 주소 목록 조회
     */
    List<String> getRegionList(Long memberId);

    /**
     * 현재 로그인 사용자의 발전소 목록 조회
     */
    List<PlantInfoView> getPlantList(Long memberId, String region);

    /**
     * 오늘 날짜 기준 시간대별 발전량 조회
     */
    List<StationHourlyGenerationDto> getHourlyGenerationList(Long memberId, String region);
}