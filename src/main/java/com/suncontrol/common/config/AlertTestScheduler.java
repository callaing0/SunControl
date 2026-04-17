package com.suncontrol.common.config;

import com.suncontrol.domain.control.NotificationController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class AlertTestScheduler {

    @Scheduled(fixedRate = 30000)
    public void keepAlive() {
        NotificationController.emitters.forEach((id, emitter) -> {
            try {
                // 🚨 broadcast 대신 ping만 쏴서 연결만 유지하세요!
                emitter.send(SseEmitter.event().name("ping").data("keep-alive"));
            } catch (Exception e) {
                NotificationController.emitters.remove(id);
            }
        });
    }
}