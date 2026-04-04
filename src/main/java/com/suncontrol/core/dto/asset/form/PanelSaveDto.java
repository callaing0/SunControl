package com.suncontrol.core.dto.asset.form;

import com.suncontrol.core.constant.asset.PanelModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PanelSaveDto {
    /// 패널등록 Form 에서 패널 DB 저장될 정보만 추출
    // TODO
    private Long inverterId;
    private PanelModel model;
    private int count;
}
