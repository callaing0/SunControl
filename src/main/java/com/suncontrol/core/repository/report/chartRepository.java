package com.suncontrol.core.repository.report;

import com.suncontrol.domain.dto.chartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface chartRepository {

    BigDecimal selectTotalGeneration(@Param("plantId") Long plantId,
                                     @Param("selectedDate") LocalDate selectedDate);

    BigDecimal selectExpectedGeneration(@Param("plantId") Long plantId,
                                        @Param("selectedDate") LocalDate selectedDate);

    BigDecimal selectAverageEfficiency(@Param("plantId") Long plantId,
                                       @Param("selectedDate") LocalDate selectedDate);

    int selectInverterCount(@Param("plantId") Long plantId);

    int selectAlertCount(@Param("plantId") Long plantId,
                         @Param("selectedDate") LocalDate selectedDate);

    List<chartDto> selectGenerationTrend(@Param("plantId") Long plantId,
                                         @Param("selectedDate") LocalDate selectedDate);

    List<chartDto> selectWeatherEfficiency(@Param("plantId") Long plantId,
                                           @Param("selectedDate") LocalDate selectedDate);

    BigDecimal selectPreviousGeneration(@Param("plantId") Long plantId,
                                        @Param("selectedDate") LocalDate selectedDate);

    BigDecimal selectAverageGeneration(@Param("plantId") Long plantId,
                                       @Param("selectedDate") LocalDate selectedDate);

    Integer selectStoppedTime(@Param("plantId") Long plantId,
                              @Param("selectedDate") LocalDate selectedDate);
}