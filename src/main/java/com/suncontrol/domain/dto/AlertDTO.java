package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class AlertDTO {
    private Long alertId;
    private String location;
    private Double efficiency;
    private String message;
    private String status;
}