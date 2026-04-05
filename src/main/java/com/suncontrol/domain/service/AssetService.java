package com.suncontrol.domain.service;

import com.suncontrol.core.dto.asset.form.InverterSaveForm;
import com.suncontrol.core.dto.asset.form.PlantSaveForm;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PanelService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.domain.form.PanelSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssetService {
    /// 자산 등록 오케스트레이터
//    private final MemberService memberService;
    private final PlantService plantService;
    private final InverterService inverterService;
    private final PanelService panelService;

    /// 발전소 정보 등록
    @Transactional
    public void savePlant(String userId, PlantSaveForm form) {
        /// TODO : memberService로 유효성 검사
//        form.getMemberId();
        plantService.save(form);
        /// TODO : ResponseEntity용 메시지 작성
    }

    /// 인버터 정보 등록
    @Transactional
    public void saveInverter(String userId, InverterSaveForm form) {
        /// TODO : memberService로 유효성 검사
        inverterService.save(form);
        /// TODO : ResponseEntity용 메시지 작성
    }

    /// 패널 정보 등록 및 인버터 '설계용량' 추가
    @Transactional
    public void savePanel(String userId, PanelSaveForm form) {
        /// TODO : memberService로 유효성 검사
        panelService.save(form.getPanelSaveDto());
        inverterService.updateCap(form.getInverterCapSurplusDto());
        /// TODO : ResponseEntity용 메시지 작성
    }

}
