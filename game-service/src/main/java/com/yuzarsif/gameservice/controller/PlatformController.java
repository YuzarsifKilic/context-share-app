package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreatePlatformRequest;
import com.yuzarsif.gameservice.service.PlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreatePlatformRequest request) {
        platformService.create(request);
        return ResponseEntity.ok("Platform created successfully");
    }
}
