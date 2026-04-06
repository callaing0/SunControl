package com.suncontrol.domain.form;

import com.suncontrol.core.constant.asset.InverterType;
import com.suncontrol.core.dto.asset.InverterDataTransferObject;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InverterSaveForm {
    /// 인버터 등록용
    @NotNull
    private Long plantId;
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal ratedCapacity;
    @NotBlank
    private String type;
    @Setter(AccessLevel.NONE)
    private BigDecimal efficiency;
    @Setter(AccessLevel.NONE)
    @NotBlank(message = "일련번호를 입력하세요")
    private String serial;

    public void setSerial(String serial) {
        if(serial != null && !serial.isBlank()) {
            this.serial = serial;
            return;
        }
        InverterType inverterType =
                InverterType.fromLabel(type);
        String prefix = (inverterType != null) ?
                inverterType.getLabel() : "DUMMY";
        LocalDateTime now = LocalDateTime.now();
        this.serial = String.format("%s-SN%d-%02d-%06d",
                prefix,
                now.getYear(),
                now.getMonthValue(),
                now.getNano() % 1000000);
    }

    public InverterDataTransferObject toDto() {
        InverterType inverterType = InverterType.fromLabel(type);
        BigDecimal finalEfficiency = efficiency != null ?
                efficiency : inverterType != null ?
                inverterType.getEfficiency() : BigDecimal.ZERO;
        return InverterDataTransferObject.builder()
                .plantId(plantId)
                .serial(serial)
                .ratedCapacity(ratedCapacity)
                .inverterType(inverterType)
                .efficiency(finalEfficiency)
                .build();
    }
}
