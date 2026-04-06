package com.suncontrol.core.dto.asset.component;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@RequiredArgsConstructor
@SuperBuilder
public class InverterBaseDto {
    private final Long inverterId;
    private final Long plantId;

    public Long getId() {
        return this.inverterId;
    }
}
