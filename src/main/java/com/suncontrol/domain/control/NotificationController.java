package com.suncontrol.domain.control;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class NotificationController {

    public static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/api/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        // 1. 타임아웃을 넉넉하게 설정 (예: 30분)
        SseEmitter emitter = new SseEmitter(30L * 60 * 1000);
        String id = "user_" + System.currentTimeMillis();
        emitters.put(id, emitter);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));
        emitter.onError((e) -> emitters.remove(id));

        try {
            // 2. [핵심] 연결 직후 "더미 데이터"를 보내서 연결이 성공했음을 브라우저에 알림
            // .reconnectTime(1000)을 넣어두면 끊겼을 때 브라우저가 1초 뒤 자동 재연결 시도함
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected")
                    .reconnectTime(1000));

            System.out.println("SSE 연결 수립: " + id);
        } catch (IOException e) {
            emitters.remove(id);
        }
        return emitter;
    }
}