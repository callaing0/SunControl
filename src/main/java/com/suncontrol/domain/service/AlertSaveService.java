package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertSaveService {
    private final Repository repository;

    public void saveEfficiencyData(AlertDTO dto) {
        // 효율 기반 알람 생성 및 저장 로직
        if (dto.getEfficiency() != null && dto.getEfficiency() < 10.0) {
            dto.setStatus("ERROR");
        }
        repository.insertAlert(dto); // 맵퍼에 insert 추가 필요
    }
}
