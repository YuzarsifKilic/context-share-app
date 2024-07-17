package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreatePublisherRequest;
import com.yuzarsif.gameservice.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreatePublisherRequest request) {
        publisherService.create(request);
        return ResponseEntity.ok("Publisher created successfully");
    }
}
