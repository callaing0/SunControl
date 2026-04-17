package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StationRepository {

    /**
     * 전체 발전소 주소 목록 조회
     */
    List<String> findRegionList();

    /**
     * 전체 발전소 목록 조회
     */
    List<PlantInfoView> findPlantInfoViewListByRegion(@Param("region") String region);

    /**
     * 오늘 날짜 기준 시간대별 발전량 조회
     */
    List<StationHourlyGenerationDto> findHourlyGenerationByRegion(@Param("region") String region);
}