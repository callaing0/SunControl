package com.suncontrol.core.repository;

import com.suncontrol.domain.dto.dashboardSummaryDto;
import com.suncontrol.domain.dto.inverterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface dashboardRepository{
    //현재출력, 금일발전량, 효율, 발전소 정보, 기상 정보, 수익 예측, 환경 기여도
    dashboardSummaryDto selectDashboardSummary(@Param("memberId") Long memberId);
    //인버터 이름, 시리얼, 상태
    List<inverterDto> selectInverters(@Param("memberId") Long memberId);
    //시간대별 발전량 조회
    List<BigDecimal> selectHourlyPower(@Param("plantId") Long plantId);
    //시간대별 일사량 조회
    List<BigDecimal> selectHourlyInsolation(@Param("plantId") Long plantId);
}
