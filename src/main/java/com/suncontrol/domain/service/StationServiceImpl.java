package com.suncontrol.domain.service;

import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.repository.report.StationRepository;
import com.suncontrol.domain.dto.StationHourlyGenerationDto;
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
     * 현재 로그인한 사용자가 보유한 발전소 주소 목록 조회
     */
    @Override
    public List<String> getRegionList(Long memberId) {
        validateMemberId(memberId);

        List<String> regionList = stationRepository.findRegionListByMemberId(memberId);
        return regionList != null ? regionList : Collections.emptyList();
    }

    /**
     * 발전소 목록 조회
     * - region 값이 null / 공백 / "all" 이면 전체 조회
     * - 허용되지 않은 region 값이면 전체 조회로 fallback
     */
    @Override
    public List<PlantInfoView> getPlantList(Long memberId, String region) {
        validateMemberId(memberId);

        String safeRegion = sanitizeRegion(memberId, region);

        List<PlantInfoView> plantList =
                stationRepository.findPlantInfoViewListByMemberIdAndRegion(memberId, safeRegion);

        return plantList != null ? plantList : Collections.emptyList();
    }

    /**
     * 오늘 날짜 기준 시간대별 발전량 조회
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
     * region 파라미터 정규화 및 검증
     */
    private String sanitizeRegion(Long memberId, String region) {

        // 전체 조회 처리
        if (region == null || region.trim().isEmpty() || "all".equalsIgnoreCase(region.trim())) {
            return "all";
        }

        String normalized = region.trim();

        // 비정상적으로 긴 입력 방어
        if (normalized.length() > 255) {
            log.warn("region 값이 너무 깁니다. memberId={}, length={}", memberId, normalized.length());
            return "all";
        }

        // 현재 사용자가 보유한 region 목록 조회
        List<String> regionList = stationRepository.findRegionListByMemberId(memberId);

        if (regionList == null || regionList.isEmpty()) {
            log.warn("사용자가 보유한 region 정보가 없습니다. memberId={}", memberId);
            return "all";
        }

        boolean matched = regionList.stream().anyMatch(normalized::equals);

        // 허용되지 않은 region 접근 방어
        if (!matched) {
            log.warn("허용되지 않은 region 접근. memberId={}, region={}", memberId, normalized);
            return "all";
        }

        return normalized;
    }
}