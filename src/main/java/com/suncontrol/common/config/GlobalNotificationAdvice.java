package com.suncontrol.common.config; // 본인 프로젝트 패키지에 맞게 수정

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalNotificationAdvice {

    @ModelAttribute
    public void addGlobalAlerts(Model model) {
        List<Map<String, String>> alertList = new ArrayList<>();

        // ID 1: 선화동 발전소 (시리얼: 260401)
        Map<String, String> alert1 = new HashMap<>();
        alert1.put("id", "1");
        alert1.put("plantName", "선화동 발전소");
        alert1.put("content", "인버터 전압 이상 감지");
        alert1.put("regDate", "2026-04-14 13:34");
        alert1.put("serial", "260401");
        alertList.add(alert1);

        // ID 2: 서울 제1발전소 (시리얼: 202604775600)
        Map<String, String> alert2 = new HashMap<>();
        alert2.put("id", "2");
        alert2.put("plantName", "서울 제1발전소");
        alert2.put("content", "테스트 장애 발생 (통신 이상)");
        alert2.put("regDate", "2026-04-16 11:15");
        alert2.put("serial", "202604775600");
        alertList.add(alert2);

        // ID 3: 솔라가드 대전 제2발전소 (시리얼: 202604626269)
        Map<String, String> alert3 = new HashMap<>();
        alert3.put("id", "3");
        alert3.put("plantName", "솔라가드 대전 제2발전소");
        alert3.put("content", "🚨 테스트 장애 발생");
        alert3.put("regDate", "2026-04-16 13:09");
        alert3.put("serial", "202604626269");
        alertList.add(alert3);

        // ID 4: 서울 제1발전소 (시리얼: 202604908107)
        Map<String, String> alert4 = new HashMap<>();
        alert4.put("id", "4");
        alert4.put("plantName", "서울 제1발전소");
        alert4.put("content", "테스트 인버터 장애 발생");
        alert4.put("regDate", "2026-04-16 15:11");
        alert4.put("serial", "202604908107");
        alertList.add(alert4);

        model.addAttribute("globalAlerts", alertList);
    }
}