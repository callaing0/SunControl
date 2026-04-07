package com.suncontrol.domain.service;

import com.suncontrol.core.repository.report.dashboardRepository;
import com.suncontrol.domain.dto.DashboardInverterDto;
import com.suncontrol.domain.dto.dashboardSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 대시보드 서비스 구현체
 *
 * 역할:
 * - Repository(MyBatis)에서 조회한 데이터를 화면에 맞게 가공
 * - 차트용 라벨 생성
 * - 차트 데이터 길이 보정
 */
@Service
@RequiredArgsConstructor
public class dashboardServiceImpl implements dashboardService {

    /**
     * 대시보드 관련 DB 조회를 담당하는 Repository
     */
    private final dashboardRepository dashboardRepository;

    /**
     * 대시보드 상단 요약 정보 조회
     *
     * 처리 순서:
     * 1. 기본 요약 정보 조회
     * 2. 발전소 ID 확인
     * 3. 시간별 발전량 / 일사량 조회
     * 4. 차트용 라벨 및 데이터 보정
     *
     * @param memberId 로그인 사용자 ID
     * @return dashboardSummaryDto
     */
    @Override
    public dashboardSummaryDto getDashboardSummary(Long memberId) {
        // 대시보드 상단 기본 정보 조회
        dashboardSummaryDto summary = dashboardRepository.selectDashboardSummary(memberId);

        // 조회 결과가 없으면 null 반환
        if (summary == null) {
            return null;
        }

        // 차트 조회에 필요한 발전소 ID 추출
        Long plantId = summary.getPlantId();

        // 발전소 ID가 없으면 차트 없이 기본 요약 정보만 반환
        if (plantId == null) {
            return summary;
        }

        // 시간별 발전량 / 시간별 일사량 조회
        List<BigDecimal> powerList = dashboardRepository.selectHourlyPower(plantId);
        List<BigDecimal> insolationList = dashboardRepository.selectHourlyInsolation(plantId);

        // 차트 라벨 및 데이터 세팅
        summary.setChartLabels(buildChartLabels());
        summary.setPowerList(normalizeSeries(powerList, 13));
        summary.setInsolationList(normalizeSeries(insolationList, 13));

        return summary;
    }

    /**
     * 사용자 소유 인버터 목록 조회
     *
     * @param memberId 로그인 사용자 ID
     * @return 인버터 리스트
     */
    @Override
    public List<DashboardInverterDto> getInverters(Long memberId) {
        return dashboardRepository.selectInverters(memberId);
    }

    /**
     * 차트 X축 라벨 생성
     *
     * 예:
     * ["06시", "07시", ..., "18시"]
     *
     * @return 시간 라벨 리스트
     */
    private List<String> buildChartLabels() {
        List<String> labels = new ArrayList<>();
        for (int hour = 6; hour <= 18; hour++) {
            labels.add(String.format("%02d시", hour));
        }
        return labels;
    }

    /**
     * 차트 데이터 길이 보정
     *
     * 역할:
     * - 데이터가 부족하면 0으로 채움
     * - 데이터가 많으면 필요한 개수만큼만 사용
     *
     * 이유:
     * - labels 개수와 data 개수를 맞춰 차트 깨짐 방지
     *
     * @param source 원본 데이터
     * @param size 최종 맞출 길이
     * @return 길이가 보정된 데이터 리스트
     */
    private List<BigDecimal> normalizeSeries(List<BigDecimal> source, int size) {
        List<BigDecimal> result = new ArrayList<>();

        // 원본 데이터 복사
        if (source != null) {
            result.addAll(source);
        }

        // 부족한 구간은 0으로 채움
        while (result.size() < size) {
            result.add(BigDecimal.ZERO);
        }

        // 데이터가 많으면 필요한 개수만큼만 사용
        if (result.size() > size) {
            return result.subList(0, size);
        }

        return result;
    }
}