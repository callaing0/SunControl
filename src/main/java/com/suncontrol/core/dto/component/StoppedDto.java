package com.suncontrol.core.dto.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoppedDto {
    private int stoppedTime;
    private int incidentCount;
}
