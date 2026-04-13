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

    private final String NOT_LOGIN_MESSAGE = "로그인 정보가 올바르지 않습니다.";
    private final String ACCESS_DENIED_MESSAGE_PLANT = "본인의 계정으로 자산관리를 진행해주세요!";
    private final String ACCESS_DENIED_MESSAGE_ASSETS = "본인 소유의 발전소만 자산관리가 가능합니다!";

    /// 발전소 정보 등록
    @Transactional
    public String savePlant(String userId, PlantSaveForm form)
            throws AccessDeniedException {
        Member member = getMember(userId);

        form.setMemberId(member.getId());

        plantService.save(form.toDto());

        return String.format("[%s] 발전소 등록 완료!", form.getName());
    }

    /// 인버터 정보 등록
    @Transactional
    public String saveInverter(String userId, InverterSaveForm form)
            throws AccessDeniedException {
        Member member = getMember(userId);
        if(!plantService.isOwnPlant(member.getId(), form.getPlantId())) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE_ASSETS);
        }
        inverterService.save(form.toDto());

        return String.format("[%s] 인버터 등록 완료!", form.getSerial());
    }

    /// 패널 정보 등록 및 인버터 '설계용량' 추가
    @Transactional
    public String savePanel(String userId, PanelSaveForm form)
            throws AccessDeniedException {
        Member member = getMember(userId);
        if(!plantService.isOwnPlant(member.getId(), form.getPlantId())) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE_ASSETS);
        }

        panelService.save(form.toDto());
        inverterService.updateCap(form.toInvCapDto());

        return String.format("[%s] 패널 %d개 등록 완료!", form.getModelName(), form.getCount());
    }

    /// BingingResult의 유효성 검사에 통과하지 못했을 경우,
    /// form 의 유효성 문구를 찾아서 json 문자열 형태로 반환하는 메서드
    /// 추후에 다른 컨트롤러에도 사용한다면 별도의 클래스로 빼서 관리할 필요성이 있음.
    private final String DEFAULT_ERR_MSG = "입력값이 잘못되었습니다";
    public Map<String, String> writeErrorMsg(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ?
                                error.getDefaultMessage() : DEFAULT_ERR_MSG,
                        (existing, replacement) -> existing + ", " + replacement
                ));
    }

    /// 자산 CRUD 메서드 중복코드 재사용을 위한 멤버 테이블 조회 공통로직
    private Member getMember(String userId) {
        Member member = memberService.findByUserId(userId);
        if(member == null) {
            throw new IllegalArgumentException(NOT_LOGIN_MESSAGE);
        }
        return member;
    }

}
