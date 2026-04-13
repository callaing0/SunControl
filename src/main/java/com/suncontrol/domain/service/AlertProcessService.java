package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.dto.AlertUpdateStatusDto;
import com.suncontrol.core.constant.alert.AlertStatus;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertProcessService {

    private final AlertListService alertListService;
    private final Repository repository;

    /**
     * 기존 유지: 대기 중인 알람 자동 분석 및 해결 처리
     */
    @Transactional
    public void processAlertAnalysis() {
        List<AlertResponseDTO> pendingAlerts = alertListService.getAlertListByStatus(AlertStatus.PENDING);

        List<AlertUpdateStatusDto> updateList = pendingAlerts.stream()
                .map(alert -> new AlertUpdateStatusDto(
                        alert.getInverterId(),
                        alert.getCreatedAt(),
                        AlertStatus.RESOLVED
                ))
                .collect(Collectors.toList());

        if (!updateList.isEmpty()) {
            repository.updateAlertStatuses(updateList);
            log.info("{}건의 알람 상태 업데이트 완료", updateList.size());
        }
    }

    /**
     * 추가: 사용자가 화면에서 직접 조치 완료를 처리할 때 사용
     */
    @Transactional
    public void resolveAlert(Long id) {
        // Enum의 코드를 직접 넘겨 DB 업데이트
        repository.updateStatus(id, AlertStatus.RESOLVED.getCode());
        log.info("ID: {} 번 알람 조치 완료 성공", id);
    }
}
