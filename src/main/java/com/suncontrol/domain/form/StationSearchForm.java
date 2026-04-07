package com.suncontrol.domain.form;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationSearchForm {

    /**
     * 지역 필터 파라미터
     */
    @Size(max = 255)
    private String region;

    /**
     * region 값 정규화
     * 처리 내용:
     * - null → "all"
     * - 공백 → "all"
     * - trim 적용
     */
    public String normalizedRegion() {

        if (region == null) {
            return "all";
        }

        String value = region.trim();

        if (value.isEmpty()) {
            return "all";
        }

        return value;
    }
}
