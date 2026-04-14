package com.suncontrol.domain.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class NotificationController {

    public static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/api/notifications/subscribe")
    public SseEmitter subscribe() {
        // 30분 타임아웃
        SseEmitter emitter = new SseEmitter(30L * 60 * 1000);
        String id = "user_" + System.currentTimeMillis();
        emitters.put(id, emitter);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));

        try {
            // 최초 연결 시 'connect' 이벤트를 확실하게 전송
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected")
                    .reconnectTime(1000)); // 연결 끊기면 1초 뒤 재시도 하라는 설정 추가
            System.out.println("SSE 구독 성공: " + id);
        } catch (IOException e) {
            emitters.remove(id);
        }
        return emitter;
    }
}