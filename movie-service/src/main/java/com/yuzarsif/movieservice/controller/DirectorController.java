package com.yuzarsif.movieservice.controller;

import com.yuzarsif.movieservice.dto.CreateDirectorRequest;
import com.yuzarsif.movieservice.service.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/directors")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping
    public ResponseEntity<String> saveDirector(@RequestBody CreateDirectorRequest request) {
        directorService.save(request);
        return ResponseEntity.ok("Director created successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> deleteDirector(@PathVariable Long id) {
        directorService.delete(id);
        return ResponseEntity.ok("Director deleted successfully");
    }
}
