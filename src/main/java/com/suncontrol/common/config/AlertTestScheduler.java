package com.suncontrol.common.config;

import com.suncontrol.domain.control.NotificationController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class AlertTestScheduler {

    // 30초마다 "나 살아있어"라고 브라우저에 신호 보냄 (끊김 방지 핵심)
    @Scheduled(fixedRate = 30000)
    public void keepAlive() {
        NotificationController.emitters.forEach((id, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("ping").data("keep-alive"));
            } catch (Exception e) {
                NotificationController.emitters.remove(id);
            }
        });
    }
}