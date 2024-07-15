package com.yuzarsif.movieservice.controller;

import com.yuzarsif.movieservice.dto.CreateStarRequest;
import com.yuzarsif.movieservice.service.StarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stars")
public class StarController {

    private final StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    @PostMapping
    public ResponseEntity<String> saveStar(@RequestBody CreateStarRequest request){
        starService.save(request);
        return ResponseEntity.ok("Star created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStar(@PathVariable Long id){
        starService.delete(id);
        return ResponseEntity.ok("Star deleted successfully");
    }
}
