package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.SeasonDto;
import com.yuzarsif.seriesservice.dto.request.CreateSeasonRequest;
import com.yuzarsif.seriesservice.service.SeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seasons")
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @PostMapping("/{seriesId}")
    public ResponseEntity<SeasonDto> createSeason(@PathVariable Long seriesId, @RequestBody CreateSeasonRequest request) {
        return ResponseEntity.ok(seasonService.createSeason(seriesId, request));
    }

    @GetMapping("/{seasonId}")
    public ResponseEntity<SeasonDto> getSeason(@PathVariable Long seasonId) {
        return ResponseEntity.ok(seasonService.getSeason(seasonId));
    }

    @GetMapping("/series/{seriesId}")
    public ResponseEntity<List<SeasonDto>> getSeasonsBySeriesId(@PathVariable Long seriesId) {
        return ResponseEntity.ok(seasonService.getSeasonsBySeriesId(seriesId));
    }
}
