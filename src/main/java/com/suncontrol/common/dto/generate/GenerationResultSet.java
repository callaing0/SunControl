package com.suncontrol.common.dto.generate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenerationResultSet {
    List<GenerationResultDto> results;
    InverterGenerationDto inverter;
}
