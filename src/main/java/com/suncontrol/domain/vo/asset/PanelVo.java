package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.constant.asset.PanelManufacturer;
import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.dto.asset.PanelDto;
import lombok.Getter;
import lombok.Setter;

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

    public PanelVo(PanelDto dto) {
        this.inverterId = dto.getInverterId();
        this.model = dto.getModel();
        this.count = dto.getCount();
        this.manufacturer = dto.getModel().getManufacturer();
    }

    public String getName() {
        return model.getName();
    }
}
