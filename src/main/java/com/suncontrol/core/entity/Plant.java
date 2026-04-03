package com.suncontrol.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Plant {
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String provinceCode;
    private String districtCode;
    private boolean isMain;
    private boolean isDeleted;
    private int azimuth;
    private int tilt;
    private LocalDateTime createdAt;
}
