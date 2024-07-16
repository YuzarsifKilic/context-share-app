package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreateSystemRequirementRequest;
import com.yuzarsif.gameservice.service.SystemRequirementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system-requirements")
public class SystemRequirementController {

    private final SystemRequirementService systemRequirementService;

    public SystemRequirementController(SystemRequirementService systemRequirementService) {
        this.systemRequirementService = systemRequirementService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateSystemRequirementRequest request) {
        systemRequirementService.create(request);
        return ResponseEntity.ok("System requirement created successfully");
    }
}
