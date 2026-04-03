package com.suncontrol.domain.dto;

import lombok.Data;

@Data
public class inverterDto {
    private Long inverterId;
    private String serial;
    private Integer statusCode;
}
