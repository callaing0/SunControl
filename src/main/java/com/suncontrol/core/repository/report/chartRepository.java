package com.suncontrol.core.repository.report;

import com.suncontrol.domain.dto.chartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface chartRepository {

    BigDecimal selectTotalGeneration(@Param("memberId") Long memberId,
                                     @Param("selectedDate") LocalDate selectedDate);

    BigDecimal selectExpectedGeneration(@Param("memberId") Long memberId,
                                        @Param("selectedDate") LocalDate selectedDate);

    BigDecimal selectAverageEfficiency(@Param("memberId") Long memberId,
                                       @Param("selectedDate") LocalDate selectedDate);

    int selectInverterCount(@Param("memberId") Long memberId);

    int selectAlertCount(@Param("memberId") Long memberId,
                         @Param("selectedDate") LocalDate selectedDate);

    List<chartDto> selectGenerationTrend(@Param("memberId") Long memberId,
                                         @Param("selectedDate") LocalDate selectedDate);

    List<chartDto> selectWeatherEfficiency(@Param("memberId") Long memberId,
                                           @Param("selectedDate") LocalDate selectedDate);
}