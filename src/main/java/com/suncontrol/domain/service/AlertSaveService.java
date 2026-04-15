package com.suncontrol.domain.service;

import com.suncontrol.core.constant.alert.AlertStatus;
import com.suncontrol.core.entity.log.AlertLog;
import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

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
        if (dto.getEfficiency() != null && dto.getEfficiency() < 10.0) {
            dto.setStatus(AlertStatus.ABNORMAL.getCode());
            dto.setMessage("발전 효율 저하 감지: " + dto.getEfficiency() + "%");
        } else {
            return; // 정상은 저장 안 하거나 따로 처리
        }

        repository.insertAlert(dto);
        log.info("데이터 저장 완료 - 지역: {}, 상태: {}", dto.getLocation(), dto.getStatus());
    }

    @Transactional
    public void changeAlertStatus(Long alertId) {
        AlertLog alert = repository.findAlertById(alertId);

        if (alert == null) {
            throw new IllegalArgumentException("해당 알람이 없습니다. id=" + alertId);
        }

        AlertStatus current = AlertStatus.fromCode(alert.getStatus());
        AlertStatus next = current.next();

        if (current == next) {
            return;
        }

        alert.setStatus(next.getCode());

        LocalDateTime now = LocalDateTime.now();

        if (next == AlertStatus.CHECKING && alert.getCheckedAt() == null) {
            alert.setCheckedAt(now);
        }

        if (next == AlertStatus.RESOLVED) {
            alert.setResolvedAt(now);

            if (alert.getCreatedAt() != null) {
                long duration = Duration.between(alert.getCreatedAt(), now).getSeconds();
                alert.setDurationSeconds((int) duration);
            }
        }

        repository.updateAlertStatus(alert);
    }
}