package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreateFeatureRequest;
import com.yuzarsif.gameservice.service.FeatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/features")
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateFeatureRequest request) {
        featureService.create(request);
        return ResponseEntity.ok("Feature created successfully");
    }
}
