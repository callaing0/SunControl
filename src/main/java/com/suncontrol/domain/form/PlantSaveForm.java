package com.suncontrol.domain.form;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.PlantDto;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class PlantSaveForm {
    /// 발전소 등록용
    private final String COORDINATE_INVALID = "잘못된 좌표 요청입니다.";

    /// 토큰에 사용자정보가 없을 경우 등록 차단

    private Long memberId;

    /// 사용자가 직접 입력하는 필드
    @NotBlank(message = "발전소 이름을 입력하세요")
    private String name;
    @NotBlank(message = "주소검색을 완료해주세요")
    private String address;

    /// 주소검색 완료 시 자동으로 채워지는 필드
    @NotNull
    @DecimalMin(value = "-90.0", message = COORDINATE_INVALID)
    @DecimalMax(value = "90.0", message = COORDINATE_INVALID)
    private BigDecimal latitude;
    @NotNull
    @DecimalMin(value = "-180.0", message = COORDINATE_INVALID)
    @DecimalMax(value = "180.0", message = COORDINATE_INVALID)
    private BigDecimal longitude;
    @NotBlank
    @Getter(AccessLevel.NONE)
    private String districtCode;

    /// 사용자가 입력하지 않아도 기본 설정값이 있는 필드
    @Min(value = -90, message = "북동쪽 방향 선택은 불가능합니다.")
    @Max(value = 90, message = "북서쪽 방향 선택은 불가능합니다.")
    private int azimuth;
    @Min(value = 20, message = "설치 각도가 너무 낮습니다.")
    @Max(value = 40, message = "설치 각도가 너무 높습니다.")
    private int tilt;

    private boolean isMain;

    /// 입력된 문자열로 지역코드 검증
    public String getDistrictCode() {
        return districtCode!= null && districtCode.length() >= 5 ?
                districtCode.substring(0, 5) : districtCode;
    }
    public District getDistrict() {
        return District.fromCode(getDistrictCode());
    }
    @AssertTrue(message = "서비스 지역이 아닙니다")
    public boolean isDistrictValid() {
        return getDistrict() != District.OUT_OF_SERVICE;
    }
    public boolean isDistrictValid(District district) {
        return district != District.OUT_OF_SERVICE;
    }

    /// form 객체를 PlantService의 입력 규격에 맞추기 위해
    /// Dto로 변환하는 메서드
    public PlantDto toDto() {
        PlantDto dto = new PlantDto();
        dto.setMemberId(this.memberId);
        dto.setName(this.name);
        dto.setAddress(this.address);
        /// 위도는 Decimal(9,6)
        dto.setLatitude(this.latitude.setScale(6, RoundingMode.HALF_UP));
        /// 경도는 Decimal(8,5)
        dto.setLongitude(this.longitude.setScale(5, RoundingMode.HALF_UP));
        District district = District.fromCode(getDistrictCode());
        dto.setDistrict(district);
        dto.setProvince(isDistrictValid(district) ?
                district.getProvince() : null);
        dto.setAzimuth(this.azimuth);
        dto.setTilt(this.tilt);
        dto.setMain(true);

        return dto;
    }
}
