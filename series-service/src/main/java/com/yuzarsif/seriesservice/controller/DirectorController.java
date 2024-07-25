package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.DirectorDto;
import com.yuzarsif.seriesservice.service.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/directors")
public class DirectorController {

    private final DirectorService directorService;


    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<List<DirectorDto>> getDirectors(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return ResponseEntity.ok(directorService.getDirectors(name, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> getDirectorById(@PathVariable Long id) {
        return ResponseEntity.ok(directorService.getDirectorById(id));
    }
}
