package com.suncontrol.core.repository.report;

import com.suncontrol.domain.dto.chartDto;
import com.suncontrol.domain.dto.chartSummaryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface chartRepository {
    List<chartSummaryDto> selectStatsSummary(@Param("memberId") Long memberId,
                                             @Param("selectedDate") LocalDate selectedDate);

    List<chartDto> selectGenerationTrend(@Param("memberId") Long memberId,
                                         @Param("selectedDate") LocalDate selectedDate);

    List<chartDto> selectWeatherEfficiency(@Param("memberId") Long memberId,
                                           @Param("selectedDate") LocalDate selectedDate);
}
