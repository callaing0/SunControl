package com.suncontrol.core.service.asset;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.PlantDetailVo;
import com.suncontrol.core.dto.asset.PlantInfoVo;
import com.suncontrol.core.dto.asset.PlantVo;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import com.suncontrol.core.dto.asset.form.PlantSaveForm;
import com.suncontrol.core.entity.asset.Plant;
import com.suncontrol.core.repository.asset.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository repository;

    public void save(PlantSaveForm form) {
        /// form을 Entity로 변환하여 저장
        repository.save(new Plant(form));
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

    public List<PlantVo> findByMemberId(Long memberId) {
        //todo
        return null;
    }

    public PlantInfoVo findInfo(Long id) {
        //todo
        return null;
    }

    public PlantDetailVo findDetail(Long id) {
        //todo
        return null;
    }

    public Map<District, List<Long>> findMapByDistrict () {
        //todo
        return Collections.emptyMap();
    }

    public List<PlantWeatherApiDto> findAllForWeatherApi () {
        //todo
        return null;
    }
}
