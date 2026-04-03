package com.suncontrol.core.entity.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PlantInfoView {
    /// Plant, Inverter 테이블을 참조한
    /// 조회용 뷰 객체
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String provinceCode;
    private String districtCode;
    private boolean isMain;
    private int azimuth;
    private int tilt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private BigDecimal capacitySum;
    /// 소속 인버터 용량합계
    /// min(ratedCapacity, measuredCapacity)

    private BigDecimal totalValue;
    /// 소속 인버터 출력합계

    private BigDecimal accumTotal;
    /// 소속 인버터 누적발전량 합계

    private BigDecimal efficiency;
    /// 출력합계 / 용량합계 * 100

    private int statusCode;
}
