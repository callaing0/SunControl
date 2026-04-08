package com.suncontrol.core.repository.report;

import com.suncontrol.domain.dto.DashboardGenerationDto;
import com.suncontrol.domain.dto.DashboardHourlyValueDto;
import com.suncontrol.domain.dto.DashboardInverterDto;
import com.suncontrol.domain.dto.DashboardPlantDto;
import com.suncontrol.domain.dto.DashboardRealtimeDto;
import com.suncontrol.domain.dto.DashboardSunTimeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface dashboardRepository {

    DashboardPlantDto selectPlantById(@Param("memberId") Long memberId,
                                      @Param("plantId") Long plantId);

    DashboardRealtimeDto selectPlantRealtime(@Param("plantId") Long plantId);

    DashboardSunTimeDto selectTodaySunTime(@Param("districtCode") String districtCode);

    BigDecimal selectLatestTemperature(@Param("districtCode") String districtCode);

    BigDecimal selectTodayInsolation(@Param("plantId") Long plantId);

    DashboardGenerationDto selectTodayGeneration(@Param("plantId") Long plantId);

    List<DashboardInverterDto> selectInvertersByPlant(@Param("memberId") Long memberId,
                                                      @Param("plantId") Long plantId);

    List<DashboardHourlyValueDto> selectHourlyPower(@Param("plantId") Long plantId);

    List<DashboardHourlyValueDto> selectHourlyInsolation(@Param("plantId") Long plantId);
}