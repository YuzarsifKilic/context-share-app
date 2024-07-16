package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.GraphicsDto;
import com.yuzarsif.gameservice.service.GraphicsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/graphics")
public class GraphicsController {

    private final GraphicsService graphicsService;

    public GraphicsController(GraphicsService graphicsService) {
        this.graphicsService = graphicsService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<GraphicsDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(graphicsService.findByName(name));
    }
}
