package com.suncontrol.domain.service;

import com.suncontrol.core.dto.asset.InverterDto;
import com.suncontrol.core.dto.asset.PanelDto;
import com.suncontrol.core.dto.asset.PlantDto;
import com.suncontrol.core.service.asset.InverterService;
import com.suncontrol.core.service.asset.PanelService;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.vo.MemberDetailVo;
import com.suncontrol.domain.vo.MyPageVo;
import com.suncontrol.domain.vo.asset.InverterDetailVo;
import com.suncontrol.domain.vo.asset.PanelVo;
import com.suncontrol.domain.vo.asset.PlantDetailVo;
import com.suncontrol.domain.vo.asset.PlantVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;
    private final PlantService plantService;
    private final InverterService inverterService;
    private final PanelService panelService;

    public MyPageVo findMyPageView(String userId) {
        // MyPageVo 생성
        MyPageVo myPageVo = new MyPageVo();

        MemberDetailVo memberDetail = new MemberDetailVo(memberService.findByUserId(userId));

        if (memberDetail.getId() == null) {
            return null;
        }

        myPageVo.setMember(memberDetail);

        /** 4/7 추가
         * 가져온 사용자의 정보를 토대로 개별 자산 정보를 추출하여
         * 최종적인 MyPageVo를 완성한다.
         * */

        // 발전소 정보 가져오기
        // 메인 발전소 id 추출 (또는 조회할 발전소 id를 파라미터로 받아야 함)
        Long mainPlantId = plantService
                .findAllByMemberId(memberDetail.getId())
                .stream()
                .filter(PlantDto::isMain)
                .map(PlantDto::getId)
                .findFirst()
                .orElse(null);

        // 발전소 상세정보 조회
        myPageVo.setPlant(
                new PlantDetailVo(
                        plantService.getInfoViewById(mainPlantId)));
        
        // 발전소의 인버터 모두가져오기
        List<InverterDto> inverters = inverterService.findAllByPlant(mainPlantId);
        PlantVo parentPlant = new PlantVo(myPageVo.getPlant());

        // 인버터의 조회정보를 삽입
        myPageVo.setInverters(inverters
                .stream()
                .map(dto -> new InverterDetailVo(parentPlant,dto))
                .collect(Collectors.toList())
        );

        // 패널의 조회정보를 삽입
        // 해당 맵의 0L 키에는 발전소 전체 패널에 대한 정보가 들어있다.
        myPageVo.setPanels(
                panelService.findDetailsByInverters(
                        // panelService에 List<Long> 인버터id를 집어넣는다
                        inverters
                                .stream()
                                .map(InverterDto::getId)
                                .toList()
                )
                        // 나온 결과값은 Map<Long, List<DTO>> 이므로
                        // 안의 내용물을 키나 인덱스는 건드리지 않고 DTO->VO로 바꾼다
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> entry
                                                .getValue()
                                                .stream()
                                                .map(PanelVo::new)
                                                .toList()
                                )
                        )
        );


        return myPageVo;
    }
}