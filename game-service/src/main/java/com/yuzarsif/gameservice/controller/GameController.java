package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.GameDto;
import com.yuzarsif.gameservice.dto.GameListDto;
import com.yuzarsif.gameservice.dto.PageResponse;
import com.yuzarsif.gameservice.dto.request.CreateGameRequest;
import com.yuzarsif.gameservice.dto.request.SearchByGenreRequest;
import com.yuzarsif.gameservice.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody CreateGameRequest request) {
        gameService.create(request);
        return ResponseEntity.ok("Game created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<GameListDto>> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(gameService.findAll(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<GameDto>> findByNameContaining(
            @RequestParam String name,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(gameService.findByNameContaining(name, page, size));
    }

    @PostMapping("/search/genre")
    public ResponseEntity<PageResponse<GameDto>> findByGenres(
            @RequestBody SearchByGenreRequest searchByGenre) {
        return ResponseEntity.ok(gameService.findByGenres(searchByGenre));
    }
}
