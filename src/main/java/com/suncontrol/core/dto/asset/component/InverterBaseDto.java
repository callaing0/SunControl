package com.suncontrol.core.dto.asset.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class InverterBaseDto {
    private Long inverterId;
    private Long plantId;

    public Long getId() {
        return this.inverterId;
    }
}
