package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertSaveService {

    private final Repository repository;

    /**
     * 외부 수집 데이터 처리 및 알람 저장
     */
    @Transactional
    public void saveAlertData(AlertSaveRequestDTO dto) {
        // 1. 장애 판단 로직 (효율이 10% 미만이면 ERROR)
        if (dto.getEfficiency() != null && dto.getEfficiency() < 10.0) {
            dto.setStatus("ERROR");
            dto.setMessage("발전 효율 저하 감지: " + dto.getEfficiency() + "%");
            log.warn("장애 감지! 지역: {}, 효율: {}%", dto.getLocation(), dto.getEfficiency());
        } else {
            dto.setStatus("NORMAL");
        }

        // 2. DB 저장
        repository.insertAlert(dto);
        log.info("데이터 저장 완료 - 지역: {}, 상태: {}", dto.getLocation(), dto.getStatus());
    }
}