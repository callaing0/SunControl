package com.suncontrol.common.config;

import com.suncontrol.domain.service.AlertListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final AlertListService alertListService;

    @ModelAttribute("globalAlertStatus")
    public Integer globalAlertStatus() {
        return alertListService.getGlobalStatus();
    }
}
