package com.suncontrol.domain.form;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationSearchForm {

    /**
     * 지역 필터 파라미터
     * - 최대 길이 제한 (보안 및 DB 보호)
     */
    @Size(max = 255)
    private String region;

    /**
     * region 값 정규화
     */
    public String normalizedRegion() {

        // null 방어
        if (region == null) {
            return "all";
        }

        String value = region.trim();

        // 공백 → 전체 조회
        if (value.isEmpty()) {
            return "all";
        }

        // "all" 통일 처리 (대소문자 무시)
        if ("all".equalsIgnoreCase(value)) {
            return "all";
        }

        // 길이 방어 (혹시 validation 우회되는 경우 대비)
        if (value.length() > 255) {
            return "all";
        }

        return value;
    }
}