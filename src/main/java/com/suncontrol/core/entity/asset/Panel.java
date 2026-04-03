package com.suncontrol.core.entity.asset;

import com.suncontrol.core.constant.asset.PanelManufacturer;
import com.suncontrol.core.constant.asset.PanelModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Panel {
    private Long id;
    private Long inverterId;
    private PanelModel model;
    private PanelManufacturer panelManufacturer;
//    private int capacity;
    private BigDecimal efficiency;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setModelName(String modelName) {
        this.model = PanelModel.getByName(modelName);
    }
    public String getModelName() {
        return this.model.getName();
    }
    public void setManufacturer(String  manufacturer) {
        this.panelManufacturer = PanelManufacturer.getByLabel(manufacturer);
    }
    public String getManufacturer() {
        return this.panelManufacturer.getLabel();
    }
    public int getCapacity() {
        return this.model.getCapacity();
    }
    public int getTotalCapacity() {
        return this.model.getCapacity() * count;
    }
}
