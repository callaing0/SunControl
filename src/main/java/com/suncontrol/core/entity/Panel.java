package com.suncontrol.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Panel {
    private Long id;
    private Long inverterId;
    private String modelName;
    private String manufacturer;
    private int capacity;
    private BigDecimal efficiency;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
