package com.suncontrol.core.dto.asset.component;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class InverterBaseDto {
    private final Long inverterId;
    private final Long plantId;

    public Long getId() {
        return this.inverterId;
    }
}
