package com.suncontrol.domain.service;

import com.suncontrol.domain.control.NotificationController;
import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertProcessService {

    private final Repository repository;

    // 리스트 조회용
    public List<AlertResponseDTO> findAll() {
        return repository.findAll();
    }

    /**
     * ✅ [중요] 실시간 자동 알림 발송용 메서드
     * 버튼 안 눌러도 알림을 보내고 싶을 때 '다른 파일'에서 이 메서드를 호출하면 됩니다.
     */
    public void sendAutomaticAlert(String message) {
        // 1. emitters가 비어있는지 로그 확인
        if (NotificationController.emitters.isEmpty()) {
            System.out.println(">>> 접속 중인 클라이언트가 없음");
            return;
        }

        NotificationController.emitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("alertUpdate") // 2. 이 이름이 HTML의 addEventListener와 같은지 확인
                        .data(message));
            } catch (Exception e) {
                NotificationController.emitters.remove(key);
            }
        });
    }

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