package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.dashboardSummaryDto
import com.suncontrol.domain.dto.inverterDto

import java.util.List;

public interface dashboardService {

    dashboardSummaryDto getDashboardSummary(Long memberId);

    List<inverterDto> getInverters(Long memberId);
}
