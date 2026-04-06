package com.suncontrol.core.service.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.dto.asset.*;
import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
import com.suncontrol.core.entity.asset.Inverter;
import com.suncontrol.core.repository.asset.InverterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InverterService {
    private final InverterRepository repository;

    public void save(InverterDataTransferObject dto) {
        repository.save(new Inverter(dto));
    }

    public void updateCap(InverterCapSurplusDto dto) {
        repository.updateCap(dto);
    }

    public void updateStatus(InverterUpdateStatus dto) {
        //todo
    }

    @Transactional
    public void updateAccumAndStatus(List<InverterUpdateDto> list) {
        int result = repository.updateLastAccumAndStatus(list);
        log.debug("{}건 저장",result);
    }

    public List<InverterDataTransferObject> findAllByPlant(Long plantId) {
        return null; //todo
    }

    public List<InverterDataTransferObject> findAllActive() {
        /// 발전데이터 생성을 위해 시스템에 등록된 모든 활성 인버터를
        /// InverterDto로 변환하여 반환
        return repository.findAllByStatusCodeBetween(
                DeviceStatus.INVERTER_NORMAL.getCode(),
                DeviceStatus.INVERTER_END.getCode())
                .stream().map(InverterDataTransferObject::new).toList();
    }

    public Map<Long, List<InverterDataTransferObject>> getInverterMapByPlantId() {
        /// 검색의 편의성을 위해 발전소 ID를 기준으로 매핑
        return findAllActive()
                .stream()
                .collect(Collectors.groupingBy(InverterDataTransferObject::getPlantId));
    }
}
