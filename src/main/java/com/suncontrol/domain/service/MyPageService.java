package com.suncontrol.domain.service;

import com.suncontrol.core.dto.asset.InverterDto;
import com.suncontrol.core.dto.asset.PanelDto;
import com.suncontrol.core.dto.asset.PlantDto;
import com.suncontrol.core.entity.Member;
import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PanelService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.vo.MemberDetailVo;
import com.suncontrol.domain.form.PasswordChangeForm;
import com.suncontrol.domain.vo.MyPageVo;
import com.suncontrol.domain.vo.asset.InverterDetailVo;
import com.suncontrol.domain.vo.asset.PanelVo;
import com.suncontrol.domain.vo.asset.PlantDetailVo;
import com.suncontrol.domain.vo.asset.PlantVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final PlantService plantService;
    private final InverterService inverterService;
    private final PanelService panelService;

    public MyPageVo findMyPageView(String userId, Long selectedPlantId) {
        MyPageVo myPageVo = new MyPageVo();

        MemberDetailVo memberDetail = new MemberDetailVo(memberService.findByUserId(userId));

        if (memberDetail.getId() == null) {
            return null;
        }

        myPageVo.setMember(memberDetail);
        log.info("{}", memberDetail.getName());

        List<PlantDto> plantList = plantService.findAllByMemberId(memberDetail.getId());

        if (plantList == null || plantList.isEmpty()) {
            myPageVo.setPlant(null);
            myPageVo.setInverters(List.of());
            myPageVo.setPanels(Map.of());
            return myPageVo;
        }

        Long targetPlantId = null;

        if (selectedPlantId != null) {
            targetPlantId = plantList.stream()
                    .map(PlantDto::getId)
                    .filter(id -> id.equals(selectedPlantId))
                    .findFirst()
                    .orElse(null);
        }

        if (targetPlantId == null) {
            targetPlantId = plantList.stream()
                    .filter(PlantDto::isMain)
                    .map(PlantDto::getId)
                    .findFirst()
                    .orElse(plantList.get(0).getId());
        }

        PlantInfoView view = plantService.getInfoViewById(targetPlantId);
        log.info("plant view : {}", view);

        myPageVo.setPlant(new PlantDetailVo(view));

        List<InverterDto> inverters = inverterService.findAllByPlant(targetPlantId);
        PlantVo parentPlant = new PlantVo(myPageVo.getPlant());
        log.info("발전소 : {}", myPageVo.getPlant());

        myPageVo.setInverters(
                inverters.stream()
                        .map(dto -> new InverterDetailVo(parentPlant, dto))
                        .collect(Collectors.toList())
        );

        List<Long> inverterIds = inverters.stream()
                .map(InverterDto::getId)
                .toList();

        if (inverterIds.isEmpty()) {
            myPageVo.setPanels(Map.of());
        } else {
            myPageVo.setPanels(
                    panelService.findDetailsByInverters(inverterIds)
                            .entrySet()
                            .stream()
                            .collect(
                                    Collectors.toMap(
                                            Map.Entry::getKey,
                                            entry -> entry.getValue()
                                                    .stream()
                                                    .map(PanelVo::new)
                                                    .toList()
                                    )
                            )
            );
        }

        return myPageVo;
    }

    public String changePassword(String userId, PasswordChangeForm passwordChangeForm) {
        Member member = memberService.findByUserId(userId);

        if (member == null) {
            return "사용자 정보를 찾을 수 없습니다.";
        }

        if (!passwordEncoder.matches(passwordChangeForm.getCurrentPassword(), member.getPassword())) {
            return "현재 비밀번호가 일치하지 않습니다.";
        }

        if (!passwordChangeForm.getNewPassword().equals(passwordChangeForm.getConfirmPassword())) {
            return "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.";
        }

        if (passwordEncoder.matches(passwordChangeForm.getNewPassword(), member.getPassword())) {
            return "새 비밀번호는 현재 비밀번호와 다르게 입력해야 합니다.";
        }

        memberService.changePassword(member, passwordEncoder.encode(passwordChangeForm.getNewPassword()));

        return "success";
    }
}