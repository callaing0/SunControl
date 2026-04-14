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

    public List<AlertResponseDTO> findAll() {
        return repository.findAll();
    }

    // 상태 변경 로직 없이 알림만 쏘는 메서드
    public void sendSimpleNotification(Long id) {
        String pushMessage = "알람 ID " + id + "번에 대한 실시간 알림 테스트입니다.";

        NotificationController.emitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("alertUpdate")
                        .data(pushMessage));
                System.out.println("알림 전송 성공: " + key);
            } catch (Exception e) {
                NotificationController.emitters.remove(key);
            }
        });
    }
    
    public int getStatusCode(Long id) {
        return 0;
    }

    public void updateStatus(Long id, int i) {
    }
}