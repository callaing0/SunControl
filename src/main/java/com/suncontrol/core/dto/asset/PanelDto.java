package com.suncontrol.core.dto.asset;

import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.entity.asset.Panel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PanelDto {
    /// 패널등록 Form 에서 패널 DB 저장될 정보만 추출
    // TODO
    private Long id;
    private Long inverterId;
    private PanelModel model;
    private int count;
    private BigDecimal totalCapacity;

    public PanelDto(Panel Entity) {
        this.id = Entity.getId();
        this.inverterId = Entity.getInverterId();
        this.model = Entity.getModel();
        this.count = Entity.getCount();
        this.totalCapacity = BigDecimal.valueOf(
                (long) model.getCapacity() * count).movePointLeft(3);
    }
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
}
