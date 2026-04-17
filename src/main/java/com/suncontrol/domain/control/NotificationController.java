package com.suncontrol.domain.control;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class NotificationController {

    // 각 탭의 고유 ID를 키로 사용하여 관리
    public static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public static void broadcast(String invId, String content) {
    }

    @GetMapping(value = "/api/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(60L * 60 * 1000);
        String id = "tab_" + System.nanoTime();
        emitters.put(id, emitter);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));
        emitter.onError((e) -> emitters.remove(id));

        // ❌ [이 부분을 삭제하세요]
        // Executors... 5초 뒤 발송 로직 삭제

        return emitter;
    }
}