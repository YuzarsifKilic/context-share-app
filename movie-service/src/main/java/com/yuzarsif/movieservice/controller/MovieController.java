package com.yuzarsif.movieservice.controller;

import com.yuzarsif.movieservice.dto.MovieDto;
import com.yuzarsif.movieservice.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin("*")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                  @RequestParam(defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(movieService.findAll(page, size));
    }

    @GetMapping("/name")
    public ResponseEntity<List<MovieDto>> findMoviesByName(@RequestParam String movieName) {
        return ResponseEntity.ok(movieService.findMoviesByName(movieName));
    }

    @GetMapping("/category")
    public ResponseEntity<List<MovieDto>> findMoviesByCategory(@RequestParam String categoryName) {
        return ResponseEntity.ok(movieService.findMoviesByCategory(categoryName));
    }

    @GetMapping("/director")
    public ResponseEntity<List<MovieDto>> findMoviesByDirector(@RequestParam Long directorId) {
        return ResponseEntity.ok(movieService.findMoviesByDirector(directorId));
    }

    @GetMapping("/star")
    public ResponseEntity<List<MovieDto>> findMoviesByStar(@RequestParam Long starId) {
        return ResponseEntity.ok(movieService.findMoviesByStar(starId));
    }

    @GetMapping("/writer")
    public ResponseEntity<List<MovieDto>> findMoviesByWriter(@RequestParam Long writerId) {
        return ResponseEntity.ok(movieService.findMoviesByWriter(writerId));
    }
}
