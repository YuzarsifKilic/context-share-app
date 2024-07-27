package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.StarDto;
import com.yuzarsif.seriesservice.dto.request.CreateStarRequest;
import com.yuzarsif.seriesservice.service.StarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class StarController {

    private final StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    @GetMapping
    public ResponseEntity<List<StarDto>> getStars(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return ResponseEntity.ok(starService.findStarsByName(name, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarDto> getStarById(@PathVariable Long id) {
        return ResponseEntity.ok(starService.getStarById(id));
    }

    @PostMapping
    public ResponseEntity<StarDto> createStar(@RequestBody CreateStarRequest request) {
        return ResponseEntity.ok(starService.createStar(request));
    }
}
