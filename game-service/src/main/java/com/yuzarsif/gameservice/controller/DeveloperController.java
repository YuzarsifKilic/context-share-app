package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreateDeveloperRequest;
import com.yuzarsif.gameservice.service.DeveloperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateDeveloperRequest request) {
        developerService.create(request);
        return ResponseEntity.ok("Developer created successfully");
    }
}
