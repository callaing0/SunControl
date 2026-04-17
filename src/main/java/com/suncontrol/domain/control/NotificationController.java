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

    @GetMapping(value = "/api/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        // 타임아웃 1시간
        SseEmitter emitter = new SseEmitter(60L * 60 * 1000);
        String id = "tab_" + System.nanoTime(); // 더 정밀한 고유 ID 생성
        emitters.put(id, emitter);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));
        emitter.onError((e) -> emitters.remove(id));

        // ✅ [핵심 수정] 이 탭(emitter)에만 5초 뒤에 첫 알림을 보냄
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            try {
                // 이 탭이 아직 살아있을 때만 발송
                if (emitters.containsKey(id)) {
                    emitter.send(SseEmitter.event()
                            .name("alertUpdate")
                            .data("🚨 [260401번 인버터] 선화동 발전소 효율 문제 발생!"));
                }
            } catch (Exception e) {
                emitters.remove(id);
            }
        }, 5, TimeUnit.SECONDS);

        // 연결 즉시 핑을 쏴서 통로를 뚫어줌 (안 끊기게)
        try {
            emitter.send(SseEmitter.event().name("ping").data("connected"));
        } catch (IOException e) {
            emitters.remove(id);
        }

        return emitter;
    }

    // 테스트 버튼용 (모든 탭 방송)
    @GetMapping("/api/notifications/test")
    public void triggerTestAlert() {
        broadcast("260401", "선화동 발전소 효율 문제 발생");
    }

    public static void broadcast(String inverterId, String errorContent) {
        String fullMessage = String.format("🚨 [%s번 인버터] %s!", inverterId, errorContent);
        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("alertUpdate").data(fullMessage));
            } catch (Exception e) {
                emitters.remove(id);
            }
        });
    }
}