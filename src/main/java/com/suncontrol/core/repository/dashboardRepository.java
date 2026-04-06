package com.suncontrol.domain.mapper;

import com.suncontrol.domain.dto.dashboardSummaryDto
import com.suncontrol.domain.dto.inverterDto
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface dashboardMapper {

    dashboardSummaryDto selectDashboardSummary(@Param("memberId") Long memberId);

    List<inverterDto> selectInverters(@Param("memberId") Long memberId);

    List<BigDecimal> selectHourlyPower(@Param("plantId") Long plantId);

    List<BigDecimal> selectHourlyInsolation(@Param("plantId") Long plantId);
}
