package com.suncontrol.domain.form;

import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.dto.asset.component.InverterBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PanelSaveForm {
    /// 패널 등록 form

    private InverterBaseDto inverter;
    private String modelName;
    private String manufacturer;
    private int count;

    public void setPlantId(Long plantId) {
        this.inverter.setPlantId(plantId);
    }
    public void setInverterId(Long inverterId) {
        this.inverter.setInverterId(inverterId);
    }
    public Long getPlantId() {
        return this.inverter.getPlantId();
    }
    public Long getInverterId() {
        return this.inverter.getInverterId();
    }
    public PanelModel getModel (String modelName) {
        return PanelModel.getByName( modelName );
    }

    public BigDecimal getMeasueredCapSurplus() {
        /// 패널 용량 W 단위를 인버터 용량 kW 단위로 변환
        return getModel(modelName) != null ? BigDecimal
                .valueOf(getModel(modelName).getCapacity())
                .multiply(BigDecimal.valueOf(this.count))
                .movePointLeft(3)
                : null; /// 모델을 못찾으면 null로 반환
    }
}