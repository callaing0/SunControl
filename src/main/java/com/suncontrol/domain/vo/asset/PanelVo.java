package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.constant.asset.PanelManufacturer;
import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.dto.asset.PanelDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class PanelVo {
    /// 패널 정보용(마이페이지)
    /// Map<Long inverterId, List<PanelVO>> 형식으로 제공
    /// 0번 인덱스에 '발전소 전체정보'
    private Long inverterId;
    private PanelModel model;
    private int count;
    private PanelManufacturer manufacturer;
    // 상수 분리
    private static final BigDecimal ONE_THOUSAND = BigDecimal.valueOf(1000);

    public PanelVo(PanelDto dto) {
        this.inverterId = dto.getInverterId();
        this.model = dto.getModel();
        this.count = dto.getCount();
        this.manufacturer = dto.getModel().getManufacturer();
    }

    public String getName() {
        return model.getName();
    }

    public int getUnitCapacity() {
        return model.getCapacity(); // W
    }

    // 계산 로직
    public BigDecimal getExpectedCapacityKw() {
        return toKw(getTotalCapacityW());
    }

    // 내부 계산
    private BigDecimal getTotalCapacityW() {
        return BigDecimal.valueOf(getUnitCapacity())
                .multiply(BigDecimal.valueOf(count));
    }

    private BigDecimal toKw(BigDecimal watt) {
        return watt.divide(ONE_THOUSAND, 2, RoundingMode.HALF_UP);
    }
}
