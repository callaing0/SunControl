package com.suncontrol.domain.service;

import com.suncontrol.core.entity.Member;
import com.suncontrol.domain.form.InverterSaveForm;
import com.suncontrol.domain.form.PlantSaveForm;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PanelService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.domain.form.PanelSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetRestService {
    /// 자산 CRUD 오케스트레이터
    private final MemberService memberService;
    private final PlantService plantService;
    private final InverterService inverterService;
    private final PanelService panelService;

    private final String DEFAULT_ERR_MSG = "입력값이 잘못되었습니다";
    private final String NOT_LOGIN_MESSAGE = "로그인 정보가 올바르지 않습니다.";
    private final String ACCESS_DENIED_MESSAGE_PLANT = "본인의 계정으로 자산관리를 진행해주세요!";
    private final String ACCESS_DENIED_MESSAGE_ASSETS = "본인 소유의 발전소만 자산관리가 가능합니다!";

    /// 발전소 정보 등록
    @Transactional
    public String savePlant(String userId, PlantSaveForm form)
            throws AccessDeniedException {
        Member member = memberService.findByUserId(userId);
        if(member == null) {
            throw new IllegalArgumentException(NOT_LOGIN_MESSAGE);
        }
        if(!form.getMemberId().equals(member.getId())) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE_PLANT);
        }

        plantService.save(form.toDto());

        return String.format("[%s] 발전소 등록 완료!", form.getName());
    }

    /// 인버터 정보 등록
    @Transactional
    public String saveInverter(String userId, InverterSaveForm form)
            throws AccessDeniedException {
        Member member = memberService.findByUserId(userId);
        if(member == null) {
            throw new IllegalArgumentException(NOT_LOGIN_MESSAGE);
        }
        if(plantService.isOwnPlant(member.getId(), form.getPlantId())) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE_ASSETS);
        }
        inverterService.save(form.toDto());

        return String.format("[%s] 인버터 등록 완료!", form.getSerial());
    }

    /// 패널 정보 등록 및 인버터 '설계용량' 추가
    @Transactional
    public String savePanel(String userId, PanelSaveForm form)
            throws AccessDeniedException {
        Member member = memberService.findByUserId(userId);
        if(member == null) {
            throw new IllegalArgumentException(NOT_LOGIN_MESSAGE);
        }
        if(plantService.isOwnPlant(member.getId(), form.getPlantId())) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE_ASSETS);
        }

        panelService.save(form.toDto());
        inverterService.updateCap(form.toInvCapDto());

        /// TODO : ResponseEntity용 메시지 작성
        return String.format("[%s] 패널 %d개 등록 완료!", form.getModelName(), form.getCount());
    }

    public Map<String, String> writeErrorMsg(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ?
                                error.getDefaultMessage() : DEFAULT_ERR_MSG,
                        (existing, replacement) -> existing
                ));
    }

}
