package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.SeriesDto;
import com.yuzarsif.seriesservice.dto.request.SearchByList;
import com.yuzarsif.seriesservice.model.Language;
import com.yuzarsif.seriesservice.service.SeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping
    public ResponseEntity<List<SeriesDto>> getSeries(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size) {
        return ResponseEntity.ok(seriesService.getSeries(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SeriesDto>> searchSeries(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestParam String name) {
        return ResponseEntity.ok(seriesService.getSeriesByName(name, page, size));
    }

    @GetMapping("/language")
    public ResponseEntity<List<SeriesDto>> getSeriesByLanguage(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestParam String language) {
        return ResponseEntity.ok(seriesService.getSeriesByLanguage(Language.valueOf(language), page, size));
    }

    @GetMapping("/most-popular")
    public ResponseEntity<List<SeriesDto>> getSeriesByMostPopular(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size) {
        return ResponseEntity.ok(seriesService.getSeriesByMostPopular(page, size));
    }

    @GetMapping("/language/most-popular") // todo fix the name
    public ResponseEntity<List<SeriesDto>> getSeriesByLanguageAndMostPopular(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestParam String language) {
        return ResponseEntity.ok(seriesService.getSeriesByLanguageAndMostPopular(Language.valueOf(language), page, size));
    }

    @GetMapping("/category")
    public ResponseEntity<List<SeriesDto>> getSeriesByCategory(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestBody SearchByList searchByList) {
        return ResponseEntity.ok(seriesService.getSeriesBySeriesCategoriesIn(searchByList, page, size));
    }

    @GetMapping("/directors")
    public ResponseEntity<List<SeriesDto>> getSeriesByDirectors(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestParam Long directorId) {
        return ResponseEntity.ok(seriesService.getSeriesByDirectorsIn(directorId, page,size));
    }

    @GetMapping("/stars")
    public ResponseEntity<List<SeriesDto>> getSeriesByStars(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestParam Long starId) {
        return ResponseEntity.ok(seriesService.getSeriesByStarsIn(starId, page, size));
    }

    @GetMapping("/writers")
    public ResponseEntity<List<SeriesDto>> getSeriesByWriters(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false)Integer size,
            @RequestParam Long writerId) {
        return ResponseEntity.ok(seriesService.getSeriesByWritersIn(writerId, page, size));
    }


}
