package com.suncontrol.domain.api;

import com.suncontrol.common.response.ResponseDto;
import com.suncontrol.domain.form.InverterSaveForm;
import com.suncontrol.domain.form.PanelSaveForm;
import com.suncontrol.domain.form.PlantSaveForm;
import com.suncontrol.domain.service.AssetRestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assets")
public class AssetRestController {

    private final AssetRestService service;
    private final String DEFAULT_ERR_MSG = "입력값이 잘못되었습니다";

    @PostMapping("/plants")
    public ResponseEntity<ResponseDto<?>> registerPlant(
            @Valid @RequestBody PlantSaveForm form,
            BindingResult bindingResult,
            Principal principal
            ) throws AccessDeniedException {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ResponseDto<>(
                    false, DEFAULT_ERR_MSG, service.writeErrorMsg(bindingResult)
            ));
        }
        String result = service.savePlant(principal.getName(), form);

        return ResponseEntity.ok(
                new ResponseDto<>(true, result, null));
    }

    @PostMapping("/inverters")
    public ResponseEntity<ResponseDto<?>> registerInverter(
            @Valid @RequestBody InverterSaveForm form,
            BindingResult bindingResult,
            Principal principal
    ) throws AccessDeniedException {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ResponseDto<>(
                    false, DEFAULT_ERR_MSG, service.writeErrorMsg(bindingResult)
            ));
        }
        String result = service.saveInverter(principal.getName(), form);

        return ResponseEntity.ok(
                new ResponseDto<>(true, result, null));
    }

    @PostMapping("/panels")
    public ResponseEntity<ResponseDto<?>> registerPanel(
            @Valid @RequestBody PanelSaveForm form,
            BindingResult bindingResult,
            Principal principal
            )  throws AccessDeniedException {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ResponseDto<>(
                    false, DEFAULT_ERR_MSG, service.writeErrorMsg(bindingResult)
            ));
        }
        String result = service.savePanel(principal.getName(), form);
        return ResponseEntity.ok(
                new ResponseDto<>(true, result, null));
    }
}
