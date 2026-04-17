package com.suncontrol.domain.vo;

import com.suncontrol.core.constant.asset.InverterType;
import com.suncontrol.core.constant.asset.PanelModel;
import com.suncontrol.core.vo.MemberDetailVo;
import com.suncontrol.domain.vo.asset.InverterDetailVo;
import com.suncontrol.domain.vo.asset.PanelVo;
import com.suncontrol.domain.vo.asset.PlantDetailVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MyPageVo {

    // 사용자 정보
    private MemberDetailVo member;

    // 발전소 정보 (추후 사용)
    private PlantDetailVo plant;

    // 인버터 정보 (추후 사용)
    private List<InverterDetailVo> inverters;

    // 패널 정보 (추후 사용)
    private Map<Long, List<PanelVo>> panels;

    private List<InverterType> inverterTypes = InverterType.LIST;

    private List<PanelModel> panelModels = PanelModel.LIST;
}