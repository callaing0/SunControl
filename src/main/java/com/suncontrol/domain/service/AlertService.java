package com.suncontrol.domain.service;

import com.suncontrol.mapper.Repository; // 이미지에서 확인한 매퍼 경로
import com.suncontrol.domain.control.NotificationController; // 2번 컨트롤러 경로
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final Repository repository;

    // 인버터 장애가 감지되었을 때 호출할 메서드
    public void processAlert(String invId, String content) {
        // 1. DB 저장 (본인 매퍼의 insert 메서드명으로 바꾸세요)
        // repository.insertAlert(invId, content);

        // 2. 🚨 모든 탭에 실시간 푸시 전송!
        NotificationController.broadcast(invId, content);
    }
}
