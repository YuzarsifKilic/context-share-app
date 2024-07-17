package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.GraphicsDto;
import com.yuzarsif.gameservice.dto.request.CreateGraphicsRequest;
import com.yuzarsif.gameservice.service.GraphicsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/graphics")
public class GraphicsController {

    private final GraphicsService graphicsService;

    public GraphicsController(GraphicsService graphicsService) {
        this.graphicsService = graphicsService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateGraphicsRequest request) {
        graphicsService.create(request);
        return ResponseEntity.ok("Graphics created successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<GraphicsDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(graphicsService.findByName(name));
    }
}
