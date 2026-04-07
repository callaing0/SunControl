package com.suncontrol.core.entity.asset;

import com.suncontrol.core.constant.asset.PanelModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Panel {
    private Long id;
    private Long inverterId;
    private PanelModel model;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
