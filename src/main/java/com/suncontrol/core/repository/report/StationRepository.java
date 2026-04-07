package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StationRepository {

    // 현재 로그인 사용자가 가진 발전소 지역 목록 조회
    List<String> findRegionListByMemberId(@Param("memberId") Long memberId);

    // 현재 로그인 사용자의 발전소 목록 조회
    List<PlantInfoView> findPlantInfoViewListByMemberIdAndRegion(@Param("memberId") Long memberId,
                                                                 @Param("region") String region);

    // 오늘 날짜 기준 시간대별 발전량 조회
    List<StationHourlyGenerationDto> findHourlyGenerationByMemberIdAndRegion(@Param("memberId") Long memberId,
                                                                             @Param("region") String region);
}
