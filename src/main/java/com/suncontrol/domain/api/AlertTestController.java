package com.suncontrol.domain.api;

import com.suncontrol.domain.service.AlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlertTestController {

    private final AlertService alertService;

    // 접속 주소: http://localhost:8081/test/alert?eff=50
    @GetMapping("/test/alert")
    public String testAlert(@RequestParam("eff") Double eff) {
        String result = alertService.checkAndCreateAlert(eff);
        return "<h1>Solar Alert Test</h1>" +
                "<h3>입력된 효율: " + eff + "%</h3>" +
                "<h2>결과: " + result + "</h2>";
    }
}