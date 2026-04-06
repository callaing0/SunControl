package com.suncontrol.core.service.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.dto.asset.*;
import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
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

    public void save(InverterDto dto) {
        repository.save(new Inverter(dto));
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

    public List<InverterDto> findAllByPlant(Long plantId) {
        return null; //todo
    }

    public List<InverterDto> findAllActive() {
        /// 발전데이터 생성을 위해 시스템에 등록된 모든 활성 인버터를
        /// InverterDto로 변환하여 반환
        return repository.findAllByStatusCodeBetween(
                DeviceStatus.INVERTER_NORMAL.getCode(),
                DeviceStatus.INVERTER_END.getCode())
                .stream().map(InverterDto::new).toList();
    }
}
