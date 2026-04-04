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
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /// 뷰 객체용 가상 필드 메서드
    /// TODO : PanelVo 구현시 해당 메서드는 PanelVo로 옮기고, model 필드와 함께 넘겨준다
    public void setModelName(String modelName) {
        this.model = PanelModel.getByName(modelName);
    }
    public String getModelName() {
        return this.model.getName();
    }
    public String getManufacturer() {
        return this.model.getManufacturer().getLabel();
    }
    public int getCapacity() {
        return this.model.getCapacity();
    }
    public int getTotalCapacity() {
        return this.model.getCapacity() * count;
    }
}
