package com.suncontrol.domain.service;

import com.suncontrol.domain.control.NotificationController;
import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertProcessService {

    private final Repository repository;

    public List<AlertResponseDTO> findAll() {
        return repository.findAll();
    }

    // ✅ 실시간 알림 전송 핵심 메서드
    public void sendAutomaticAlert(String message) {
        if (NotificationController.emitters.isEmpty()) {
            System.out.println(">>> [SSE] 연결된 브라우저가 없습니다.");
            return;
        }

        NotificationController.emitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("alertUpdate")
                        .data(message));
                System.out.println(">>> [SSE] 알림 발송 성공: " + message);
            } catch (IOException e) {
                NotificationController.emitters.remove(key);
            }
        });
    }

    // ✅ 테스트 버튼용 메서드
//    public void sendSimpleNotification(Long id) {
//        sendAutomaticAlert("🚨 [테스트] 장비 장애 감지! (ID: " + id + ")");
//    }

    // 2. 테스트용 (기존 버튼 기능 유지)
    public void sendSimpleNotification(Long id) {
        sendAutomaticAlert("🚨 수동 테스트 알림: " + id + "번 장비 확인 필요!");
    }

    public int getStatusCode(Long id) {
        return 0;
    }

    public void updateStatus(Long id, int i) {
    }

}