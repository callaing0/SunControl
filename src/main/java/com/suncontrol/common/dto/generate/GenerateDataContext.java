package com.suncontrol.common.dto.generate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GenerateDataContext {
    private LocalDateTime current;
    private InverterGenerationDto inv;
    private GenerateCalcBase base;
    private GenerateValueDto dto;
}
