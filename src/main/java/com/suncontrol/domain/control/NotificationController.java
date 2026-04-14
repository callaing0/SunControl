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
        SseEmitter emitter = new SseEmitter(30L * 60 * 1000);
        String id = "user_" + System.currentTimeMillis();
        emitters.put(id, emitter);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));

        try {
            emitter.send(SseEmitter.event().name("connect").data("Connected"));
        } catch (IOException e) {
            emitters.remove(id);
        }
        return emitter;
    }
}
