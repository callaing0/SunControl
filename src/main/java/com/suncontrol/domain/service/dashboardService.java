package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.DashboardInverterDto;
import com.suncontrol.domain.dto.dashboardSummaryDto;

import java.util.List;

public interface dashboardService {

    dashboardSummaryDto getDashboardSummary(Long memberId, Long plantId);

    List<DashboardInverterDto> getInverters(Long memberId, Long plantId);

    String getLastUpdateTime();
}