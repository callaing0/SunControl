package com.suncontrol.common.config;

import com.suncontrol.mapper.Repository; // 로그에 찍혔던 그 매퍼
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalNotificationAdvice {

    private final Repository repository;

    /**
     * 어떤 컨트롤러가 실행되든 이 메서드가 먼저 실행되어
     * 타임리프 화면(${globalAlerts})에 데이터를 넣어줍니다.
     */
    @ModelAttribute
    public void addGlobalAlerts(Model model) {
        try {
            // ✅ DB에서 최신 알림 이력을 가져옵니다.
            // 매퍼 XML의 select id가 'findAll'인지 꼭 확인하세요!
            var recentAlerts = repository.findAll();

            // 모든 탭(대시보드 등)의 HTML에서 이 데이터를 사용할 수 있게 됩니다.
            model.addAttribute("globalAlerts", recentAlerts);

        } catch (Exception e) {
            model.addAttribute("globalAlerts", List.of());
            System.err.println("🚨 전역 알림 데이터 로드 실패: " + e.getMessage());
        }
    }
}