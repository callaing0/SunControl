package com.suncontrol.domain.api;

import com.suncontrol.domain.service.AssetRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssetRestController {
    private final AssetRestService service;
}
