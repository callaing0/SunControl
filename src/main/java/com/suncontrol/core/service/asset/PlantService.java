package com.suncontrol.core.service.asset;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.*;
import com.suncontrol.core.entity.asset.Plant;
import com.suncontrol.core.entity.view.PlantInfoView;
import com.suncontrol.core.repository.asset.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository repository;

    public void save(PlantDto dto) {
        /// form을 Entity로 변환하여 저장
        repository.save(new Plant(dto));
    }

    @Transactional
    public void changeMain(Long memberId, Long plantId) {
        /// 소유자의 모든 발전소 '대표여부' 초기화
        repository.resetMain(memberId);
        /// 지정한 발전소를 '대표' 지정
        repository.setMain(plantId);
    }

    public boolean isOwnPlant(Long memberId, Long plantId) {
        /// 발전소가 소유주의 것인지 확인하는 메서드
        return repository.existsByIdAndMemberId(memberId, plantId);
    }

    public List<PlantDto> findAllByMemberId(Long memberId) {
        return repository.findAllByMemberIdAndIsDeletedFalse(memberId)
                .stream().map(PlantDto::new).collect(Collectors.toList());
    }

    public PlantInfoView getInfoViewById(Long id) {
        /// 도메인 서비스가 큰 덩어리 뷰 객체를 넘기면
        /// 각 오케스트레이터가 알아서 필요한 만큼 잘라쓰는 구조
        return repository.findPlantInfoViewById(id);
    }

    public List<PlantDto> findAllActive () {
        return repository.findAllByIsDeletedFalse()
                .stream().map(PlantDto::new).collect(Collectors.toList());
    }

    public Map<District, List<Long>> getPlantMapByDistrict() {
        /// 에너지 생성을 위한 살아있는 발전소 정보 가져오기
        /// 다른 세부정보는 불필요정보이므로 지역에 따라 맵으로 카테고리화하고 id만 추출한다
        return findAllActive()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                PlantDto::getDistrict,
                                Collectors.mapping(
                                        PlantDto::getId,
                                        Collectors.toList()
                                )
                        )
                );
    }

    /// plant_info_view에서 COUNT, SUM 을 통해 추출, 수집 주기는 "60"이라는 정수를 시스템 상수로 받아온다.
    public MainSummaryDto getMainSummary() {
        return repository.countPlantsAndSumTotalAccum();
    }
}
