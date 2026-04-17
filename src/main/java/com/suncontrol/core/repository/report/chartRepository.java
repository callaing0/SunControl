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

    BigDecimal selectMonthlyTotal(@Param("plantId") Long plantId,
                                  @Param("month") String month);

    BigDecimal selectMonthlyPrevious(@Param("plantId") Long plantId,
                                     @Param("month") String month);

    BigDecimal selectMonthlyAverage(@Param("plantId") Long plantId,
                                    @Param("month") String month);

    BigDecimal selectMonthlyExpected(@Param("plantId") Long plantId,
                                     @Param("month") String month);

    Integer selectMonthlyStopped(@Param("plantId") Long plantId,
                                 @Param("month") String month);

    List<chartDto> selectMonthlyTrend(@Param("plantId") Long plantId);

    List<chartDto> selectMonthlyWeather(@Param("plantId") Long plantId,
                                        @Param("month") String month);
}