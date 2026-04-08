package com.suncontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DashboardSunTimeDto {
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
}