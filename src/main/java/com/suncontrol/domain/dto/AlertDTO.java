package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class AlertDTO {
    private Long id;
    private String detectTime;
    private String resolvedTime;
    private String location;
    private String status;
}