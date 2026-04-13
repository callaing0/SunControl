package com.suncontrol.domain.form;

import com.suncontrol.core.constant.asset.PanelManufacturer;
import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
import com.suncontrol.core.dto.asset.PanelDto;
import com.suncontrol.core.dto.component.InverterBaseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PanelSaveForm {
    /// 패널 등록 form

    @NotNull
    private InverterBaseDto inverter;
    @NotBlank(message = "패널 모델을 입력해주세요")
    private String modelName;
    @NotBlank(message = "시스템에 등록된 패널 모델이 아닙니다")
    @Setter(AccessLevel.NONE)
    private PanelModel model;
    @NotBlank(message = "패널 제조사를 입력해주세요")
    @Getter(AccessLevel.NONE)
    private String manufacturer;
    @Getter(AccessLevel.NONE)
    private Integer capacity;
    @Getter(AccessLevel.NONE)
    private BigDecimal efficiency;
    @NotNull
    @Min(value = 1)
    private int count;

/// InverterCapSurplusDto 용 가상필드
    public Long getPlantId() {
        return this.inverter.getPlantId();
    }
    public BigDecimal getMeasuredCapSurplus() {
        /// 패널 용량 W 단위를 인버터 용량 kW 단위로 변환
        return model != null ? BigDecimal
                .valueOf(model.getCapacity())
                .multiply(BigDecimal.valueOf(this.count))
                .movePointLeft(3)
                : null; /// 모델을 못찾으면 null로 반환
    }
/// InverterCapSurplusDto 및 PanelSaveDto 용 가상필드
   public Long getInverterId() {
        return this.inverter.getInverterId();
    }
    public void setModel(String modelName) {
        this.model = PanelModel.getByName(modelName);
    }
    private PanelManufacturer getManufacturer() {
        return PanelManufacturer.getByLabel(manufacturer);
    }


    /// 시스템에 등록된 모델명일 경우 자동으로 스펙 가져와서 반환하기
    public int getCapacity() {
        if(model != null) {
            return model.getCapacity();
        }
        return this.capacity != null ? this.capacity : 0;
    }

    public BigDecimal getEfficiency() {
        if(model != null) {
            return model.getEfficiency();
        }
        return this.efficiency != null ? this.efficiency : BigDecimal.ZERO;
    }

    /// PanelSaveForm 은 domain 패키지에 있으므로 core 패키지의 dto 생성자에 넣을 수 없다
    /// 이 데이터 클래스 내부에서 panel 테이블과 inverter 테이블에
    /// INSERT, UPDATE 할 핵심 내용을 두 dto로 분리 추출하도록 한다.

    /// panel 테이블에 저장될 내용
    public PanelDto toDto() {
        /*
         * TODO : 시스템에 등록되어 있지 않은 모델/제조사/용량/효율 을 감지하고
         * TODO : '기존에 없던 새 패널 정보' 를 시스템에 추가 할 수 있어야 한다
         */
        PanelDto dto = new PanelDto();
        dto.setInverterId(getInverterId());
        dto.setModel(model);
        dto.setCount(count);
        return dto;
    }
    /// inverter 테이블에 업데이트 될 내용
    public InverterCapSurplusDto toInvCapDto() {
        return InverterCapSurplusDto.builder()
                .plantId(getPlantId())
                .inverterId(getInverterId())
                .measuredCapSurplus(getMeasuredCapSurplus())
                .build();
    }

}