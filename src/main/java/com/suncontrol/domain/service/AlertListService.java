package com.suncontrol.domain.service;

import com.suncontrol.core.constant.alert.AlertStatus;
import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertListService {

    private final Repository repository;

    /**
     * 알람 목록 조회 (지역 필터링 포함)
     */
    public List<AlertResponseDTO> getAlertList(String location) {
        log.info("알람 목록 조회 요청 - 지역: {}", (location != null ? location : "전체"));

        if (location == null || location.isEmpty()) {
            return repository.findAll();
        }
        return repository.findByLocation(location);
    }

    public List<AlertResponseDTO> getAlertListByStatus(AlertStatus alertStatus) {
        return List.of();
    }
}
