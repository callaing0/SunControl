package com.suncontrol.core.entity.log;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RadiationLog {
    private Long id;
    private Long plantId;
    private LocalDateTime baseTime;
    private double gti;
    private double gtiInstance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
