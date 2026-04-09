package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertSaveRequestDTO; // 바뀐 이름 확인!
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertSaveService {
    private final Repository repository;

    public void saveAlertData(AlertSaveRequestDTO dto) {
        if (dto.getEfficiency() != null && dto.getEfficiency() < 10.0) {
            dto.setStatus("ERROR");
        }
        repository.insertAlert(dto);
    }
}