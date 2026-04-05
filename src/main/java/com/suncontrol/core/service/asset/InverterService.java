package com.suncontrol.core.service.asset;

import com.suncontrol.core.dto.asset.*;
import com.suncontrol.core.dto.asset.form.InverterCapSurplusDto;
import com.suncontrol.core.dto.asset.form.InverterSaveForm;
import com.suncontrol.core.entity.asset.Inverter;
import com.suncontrol.core.repository.asset.InverterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InverterService {
    private final InverterRepository repository;

    public void save(InverterSaveForm form) {
        repository.save(new Inverter(form));
    }

    public void updateCap(InverterCapSurplusDto dto) {
        repository.updateCap(dto);
    }

    public void updateStatus(InverterUpdateStatus dto) {
        //todo
    }

    public void updateAccum(InverterUpdateEnergyDto dto) {
        //todo
    }

    public List<InverterInfoVo> findInfosByPlant(Long plantId) {
        return null; //todo
    }

    public List<InverterDetailVo> findDetailsByPlant(Long plantId) {
        return null; //todo
    }

    public Map<Long, List<InverterGenerationDto>> findAllByPlant() {
        return Collections.emptyMap();
        //todo 발전데이터 생성을 위해 시스템에 등록된 모든 활성 인버터를
        // 발전소 id 를 기준으로 한 Map에 저장하여 반환
    }
}
