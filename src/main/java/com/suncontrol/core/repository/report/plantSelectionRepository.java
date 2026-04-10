package com.suncontrol.core.repository.report;

import com.suncontrol.domain.dto.PlantSelectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface plantSelectionRepository {

    List<PlantSelectDto> selectPlantListByMemberId(@Param("memberId") Long memberId);

    PlantSelectDto selectPlantById(@Param("memberId") Long memberId,
                                   @Param("plantId") Long plantId);
}
