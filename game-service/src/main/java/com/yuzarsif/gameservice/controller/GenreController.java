package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreateGenreRequest;
import com.yuzarsif.gameservice.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateGenreRequest request) {
        genreService.create(request);
        return ResponseEntity.ok("Genre created successfully");
    }
}
