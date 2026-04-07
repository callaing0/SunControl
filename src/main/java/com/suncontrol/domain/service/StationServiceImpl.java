package com.suncontrol.domain.service;
import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.repository.report.StationRepository;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;
import com.suncontrol.domain.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StationServiceImpl implements StationService {

    /**
     * 발전소 및 발전량 조회용 Repository
     */
    private final StationRepository stationRepository;

    /**
     * 현재 로그인한 사용자가 보유한 발전소들의 지역 목록을 조회
     */
    @Override
    public List<String> getRegionList(Long memberId) {
        validateMemberId(memberId);

        List<String> regionList = stationRepository.findRegionListByMemberId(memberId);

        // null 방어 (MyBatis 결과 null 방지)
        return regionList != null ? regionList : Collections.emptyList();
    }

    /**
     * 발전소 리스트 조회
     * PlantInfoView를 사용하여 발전소 + 집계 데이터(용량, 출력 등)를 함께 반환
     */
    @Override
    public List<PlantInfoView> getPlantList(Long memberId, String region) {
        validateMemberId(memberId);

        // region 파라미터 안전 처리
        String safeRegion = sanitizeRegion(memberId, region);

        List<PlantInfoView> plantList =
                stationRepository.findPlantInfoViewListByMemberIdAndRegion(memberId, safeRegion);

        return plantList != null ? plantList : Collections.emptyList();
    }

    /**
     * 시간대별 발전량 조회
     * Chart.js에서 사용할 데이터 생성용
     */
    @Override
    public List<StationHourlyGenerationDto> getHourlyGenerationList(Long memberId, String region) {
        validateMemberId(memberId);

        String safeRegion = sanitizeRegion(memberId, region);

        List<StationHourlyGenerationDto> generationList =
                stationRepository.findHourlyGenerationByMemberIdAndRegion(memberId, safeRegion);

        return generationList != null ? generationList : Collections.emptyList();
    }

    /**
     * 사용자 ID 유효성 검증
     */
    private void validateMemberId(Long memberId) {
        if (memberId == null || memberId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
        }
    }

    /**
     * region 파라미터 안전 처리
     * 처리 내용:
     * 1. null / 공백 → "all" 처리
     * 2. 길이 제한 (255 초과 방어)
     * 3. 사용자가 실제 보유한 지역인지 검증
     */
    private String sanitizeRegion(Long memberId, String region) {

        // null 또는 공백이면 전체 조회
        if (region == null || region.trim().isEmpty() || "all".equalsIgnoreCase(region.trim())) {
            return "all";
        }

        String normalized = region.trim();

        // 비정상적으로 긴 값 방어 (보안)
        if (normalized.length() > 255) {
            log.warn("region 값이 너무 깁니다. memberId={}, length={}", memberId, normalized.length());
            return "all";
        }

        // 실제 사용자가 보유한 지역인지 검증
        List<String> regionList = getRegionList(memberId);
        boolean matched = regionList.stream().anyMatch(normalized::equals);

        // 허용되지 않은 지역 접근 방어
        if (!matched) {
            log.warn("허용되지 않은 region 접근. memberId={}, region={}", memberId, normalized);
            return "all";
        }

        return normalized;
    }
}