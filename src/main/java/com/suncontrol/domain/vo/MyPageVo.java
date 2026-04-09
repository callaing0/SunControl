package com.suncontrol.domain.vo;

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

    // TODO:발전소 정보 (추후 사용)
    private PlantDetailVo plant;

    // TODO:인버터 정보 (추후 사용)
    private List<InverterDetailVo> inverters;

    // TODO:패널 정보 (추후 사용)
    private Map<Long, List<PanelVo>> panels;
}