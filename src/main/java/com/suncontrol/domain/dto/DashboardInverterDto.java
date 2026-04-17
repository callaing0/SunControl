package com.suncontrol.domain.dto;

import com.suncontrol.core.constant.asset.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardInverterDto {
    private Long id;
    private Long plantId;
    private String serial;
    private Integer status;;
}
