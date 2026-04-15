package com.suncontrol.domain.service;

import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertUpdateService {

    private final Repository repository;

    /**
     * 알람 상태 변경 (확인 중, 조치 완료 처리)
     * @param id 알람 ID
     * @param status 변경할 상태값 (1: 확인 중, 2: 조치 완료)
     */
    @Transactional
    public void updateAlertStatus(Long id, int status) {
        log.info("알람 상태 변경 요청 - ID: {}, 변경 상태: {}", id, status);

        // Repository(Mapper)의 업데이트 메서드 호출
        int result = repository.updateStatus(id, status);

        if (result > 0) {
            log.info("알람 상태 변경 완료 - ID: {}", id);
        } else {
            log.error("알람 상태 변경 실패 - 해당 ID를 찾을 수 없음: {}", id);
            throw new RuntimeException("알람 상태 업데이트에 실패했습니다.");
        }
    }
}