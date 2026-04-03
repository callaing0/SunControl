package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.dashboardSummaryDto;
import com.suncontrol.domain.dto.inverterDto;
import com.suncontrol.core.repository.dashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class dashboardServiceImpl implements dashboardService {

    private final dashboardMapper dashboardMapper;

    // 대시보드 요약 정보 조희
    @Override
    public dashboardSummaryDto getDashboardSummary(Long memberId) {
        // 대시보드 상단 정보 조회
        dashboardSummaryDto summary = dashboardMapper.selectDashboardSummary(memberId);

        // 발전소 and 조회 결과가 없을 경우
        if (summary == null) {
            return null;
        }
        // 차트 데이터 조회 (발전소 ID가 없을 경우 기본 요약 정보만 반환)
        Long plantId = summary.getPlantId();
        if (plantId == null) {
            return summary;
        }

        // 시간별 발전량 / 시간별 일사량 조회
        List<BigDecimal> powerList = dashboardMapper.selectHourlyPower(plantId);
        List<BigDecimal> insolationList = dashboardMapper.selectHourlyInsolation(plantId);

        // 차트 라벨 및 데이터 세팅
        summary.setChartLabels(buildChartLabels());
        summary.setPowerList(normalizeSeries(powerList, 13));
        summary.setInsolationList(normalizeSeries(insolationList, 13));

        return summary;
    }

    // 사용자 소유 인버터 목록 조회
    @Override
    public List<inverterDto> getInverters(Long memberId) {
        return dashboardMapper.selectInverters(memberId);
    }

    // 차트 X축 라벨 생성
    private List<String> buildChartLabels() {
        List<String> labels = new ArrayList<>();
        for (int hour = 6; hour <= 18; hour++) {
            labels.add(String.format("%02d시", hour));
        }
        return labels;
    }

    // 차트 깨짐 방지 (차트에 들어갈 데이터 개수를 일정하게)
    private List<BigDecimal> normalizeSeries(List<BigDecimal> source, int size) {
        List<BigDecimal> result = new ArrayList<>();

        //원본 데이터 있으면 복사
        if (source != null) {
            result.addAll(source);
        }

        // 부족한 구간은 0으로 채움
        while (result.size() < size) {
            result.add(BigDecimal.ZERO);
        }

        // 데이터를 필요한 개수만큼만 사용
        if (result.size() > size) {
            return result.subList(0, size);
        }

        return result;
    }
}
