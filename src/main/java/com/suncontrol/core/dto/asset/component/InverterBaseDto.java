package com.suncontrol.core.dto.asset.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InverterBaseDto {
    private Long inverterId;
    private Long plantId;

    public void setId(Long id) {
        this.inverterId = id;
    }
    public Long getId() {
        return this.inverterId;
    }
}
