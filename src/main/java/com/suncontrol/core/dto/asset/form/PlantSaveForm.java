package com.suncontrol.core.dto.asset.form;

import com.suncontrol.core.constant.common.District;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlantSaveForm {
    /// 발전소 등록용

    /// 토큰에 사용자정보가 없을 경우 등록 차단
    @NotNull
    private Long memberId;

    /// 사용자가 직접 입력하는 필드
    @NotBlank(message = "발전소 이름을 입력하세요")
    private String name;
    @NotBlank(message = "주소검색을 완료해주세요")
    private String address;

    /// 주소검색 완료 시 자동으로 채워지는 필드
    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal latitude;
    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal longitude;
    @NotNull(message = "서비스 지역이 아닙니다")
    private District district;

    /// 사용자가 입력하지 않아도 기본 설정값이 있는 필드
    @Min(value = -90, message = "북동쪽 방향 선택은 불가능합니다.")
    @Max(value = 90, message = "북서쪽 방향 선택은 불가능합니다.")
    private int azimuth;
    @Min(value = 20, message = "설치 각도가 너무 낮습니다.")
    @Max(value = 40, message = "설치 각도가 너무 높습니다.")
    private int tilt;

    /// 입력된 문자열로 지역코드 검증
    public void setDistrictCode(String districtCode) {
        this.district = District.fromCode(districtCode);
    }

    public String getDistrictCode() {
        return this.district != null ?
                district.getCode() : null;
    }
}
