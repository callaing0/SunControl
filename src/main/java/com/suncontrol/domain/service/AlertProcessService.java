package com.suncontrol.domain.service;

import com.suncontrol.core.entity.log.AlertLog;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertProcessService {

    private final Repository repository;

    @Transactional // <--- 매우 중요: 이게 없으면 업데이트 후 커밋이 안 될 수 있습니다.
    public void resolveAlert(Long id) {
        // 1. 현재 데이터 조회
        AlertLog log = repository.findById(id);
        if (log == null) {
            System.out.println("ID " + id + "번 데이터를 찾을 수 없습니다.");
            return;
        }

        int currentStatus = log.getStatus();
        int nextStatus = currentStatus;
        LocalDateTime checkedAt = log.getCheckedAt();
        LocalDateTime resolvedAt = log.getResolvedAt();

        // 2. 상태 전환 로직 (0:대기 -> 1:확인중 -> 2:조치완료)
        if (currentStatus == 0) {
            nextStatus = 1;
            checkedAt = LocalDateTime.now();
        } else if (currentStatus == 1) {
            nextStatus = 2;
            resolvedAt = LocalDateTime.now();
        }

        // 3. DB 업데이트 호출
        repository.updateAlertProcess(id, nextStatus, checkedAt, resolvedAt);

        System.out.println("업데이트 완료 - ID: " + id + ", Next Status: " + nextStatus);
    }

    public int getStatusCode(Long id) {
        return 0;
    }

    public void updateStatus(Long id, int i) {
    }
}