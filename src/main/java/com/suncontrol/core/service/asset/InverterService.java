package com.suncontrol.core.service.asset;

import com.suncontrol.core.constant.asset.DeviceStatus;
import com.suncontrol.core.dto.asset.*;
import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
import com.suncontrol.core.entity.asset.Inverter;
import com.suncontrol.core.repository.asset.InverterRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
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

    public void save(InverterDto dto) {
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
        int result = 0;
        if(list != null && !list.isEmpty()) {
            result = repository.updateLastAccumAndStatus(list);
        }
        log.debug("{}건 저장",result);
    }

    public List<InverterDto> findAllByPlant(Long plantId) {
        return DataCollectorsUtil.toDataList(
                repository.findAllByPlantId(plantId), InverterDto::new);
    }

    public List<InverterDto> findAllActive() {
        /// 발전데이터 생성을 위해 시스템에 등록된 모든 활성 인버터를
        /// InverterDto로 변환하여 반환
        return DataCollectorsUtil.toDataList(
                repository.findAllByStatusCodeBetween(
                        DeviceStatus.INVERTER_NORMAL.getCode(),
                        DeviceStatus.INVERTER_END.getCode()
                ), InverterDto::new);
    }
}
