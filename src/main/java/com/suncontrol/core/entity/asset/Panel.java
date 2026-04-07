package com.suncontrol.core.entity.asset;

import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.dto.asset.PanelDto;
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

    public Panel(PanelDto dto) {
        this.inverterId = dto.getInverterId();
        this.model = dto.getModel();
        this.count = dto.getCount();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getModelName() {
        return model.getName();
    }
    public void setModelName(String modelName) {
        this.model = PanelModel.getByName(modelName);
    }
}
