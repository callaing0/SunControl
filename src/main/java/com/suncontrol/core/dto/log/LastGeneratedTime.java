package com.suncontrol.core.dto.log;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LastGeneratedTime {
    private Long InverterId;
    private LocalDateTime baseTime;
}
