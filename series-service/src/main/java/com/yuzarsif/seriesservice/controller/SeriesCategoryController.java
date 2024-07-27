package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.SeriesCategoryDto;
import com.yuzarsif.seriesservice.service.SeriesCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series-categories")
public class SeriesCategoryController {

    private final SeriesCategoryService seriesCategoryService;

    public SeriesCategoryController(SeriesCategoryService seriesCategoryService) {
        this.seriesCategoryService = seriesCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SeriesCategoryDto>> getSeriesCategories() {
        return ResponseEntity.ok(seriesCategoryService.getSeriesCategories());
    }
}
