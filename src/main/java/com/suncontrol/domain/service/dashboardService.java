package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.DashboardInverterDto;
import com.suncontrol.domain.dto.dashboardSummaryDto;

import java.util.List;

public interface dashboardService {
    // 대시보드 요약 데이터 조회
    dashboardSummaryDto getDashboardSummary(Long memberId);

    // 사용자 소유 인버터 조회
    List<DashboardInverterDto> getInverters(Long memberId);
}