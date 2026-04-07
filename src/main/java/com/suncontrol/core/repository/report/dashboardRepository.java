package com.suncontrol.core.repository.report;

import com.suncontrol.domain.dto.DashboardInverterDto;
import com.suncontrol.domain.dto.dashboardSummaryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface dashboardRepository {

    dashboardSummaryDto selectDashboardSummary(@Param("memberId") Long memberId);

    List<DashboardInverterDto> selectInverters(@Param("memberId") Long memberId);

    List<BigDecimal> selectHourlyPower(@Param("plantId") Long plantId);

    List<BigDecimal> selectHourlyInsolation(@Param("plantId") Long plantId);
}