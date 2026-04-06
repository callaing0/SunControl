package com.suncontrol.core.repository;

import com.suncontrol.domain.dto.dashboardSummaryDto;
import com.suncontrol.core.dto.asset.InverterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface dashboardRepository {

    dashboardSummaryDto selectDashboardSummary(@Param("memberId") Long memberId);

    List<InverterDto> selectInverters(@Param("memberId") Long memberId);

    List<BigDecimal> selectHourlyPower(@Param("plantId") Long plantId);

    List<BigDecimal> selectHourlyInsolation(@Param("plantId") Long plantId);
}
