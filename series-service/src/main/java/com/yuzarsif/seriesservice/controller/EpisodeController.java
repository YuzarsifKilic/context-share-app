package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.EpisodeDto;
import com.yuzarsif.seriesservice.dto.request.CreateEpisodeRequest;
import com.yuzarsif.seriesservice.service.EpisodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping("/{seasonId}")
    public ResponseEntity<EpisodeDto> createEpisode(@PathVariable Long seasonId, @RequestBody CreateEpisodeRequest request) {
        EpisodeDto episodeDto = episodeService.createEpisode(seasonId, request);
        return ResponseEntity.ok(episodeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDto> getEpisode(@PathVariable Long id) {
        return ResponseEntity.ok(episodeService.getEpisode(id));
    }

    @GetMapping("/season/{seasonId}")
    public ResponseEntity<List<EpisodeDto>> getEpisodesBySeasonId(@PathVariable Long seasonId) {
        return ResponseEntity.ok(episodeService.getEpisodesBySeasonId(seasonId));
    }
}
