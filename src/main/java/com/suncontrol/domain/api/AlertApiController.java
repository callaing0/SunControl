package com.suncontrol.domain.api;

import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import com.suncontrol.domain.service.AlertSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class AlertApiController {

    private final AlertSaveService alertSaveService;

    @PostMapping("/alert")
    public String receiveAlert(@RequestBody AlertSaveRequestDTO dto) {
        alertSaveService.saveAlertData(dto);
        return "success";
    }

    @PostMapping("/alert/{id}/status")
    public String changeAlertStatus(@PathVariable Long id) {
        alertSaveService.changeAlertStatus(id);
        return "success";
    }
}