package com.suncontrol.domain.api;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.domain.service.AlertSaveService; // 전용 서비스 사용
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class AlertApiController {

    private final AlertSaveService alertSaveService;

    @PostMapping("/alert")
    public String receiveAlert(@RequestBody AlertDTO dto) {
        // 효율 및 메시지 저장 중심의 로직 수행
        alertSaveService.saveEfficiencyData(dto);
        return "Data received and processed";
    }
}