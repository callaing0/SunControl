package com.suncontrol.domain.mapper;

import com.suncontrol.domain.dto.statsChartDto;
import com.suncontrol.domain.dto.statsSummaryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface statsMapper {

    List<statsSummaryDto> selectStatsSummary(@Param("memberId") Long memberId,
                                             @Param("selectedDate") String selectedDate);

    List<statsChartDto> selectGenerationTrend(@Param("memberId") Long memberId,
                                              @Param("selectedDate") String selectedDate);

    List<statsChartDto> selectWeatherEfficiency(@Param("memberId") Long memberId,
                                                @Param("selectedDate") String selectedDate);
}
