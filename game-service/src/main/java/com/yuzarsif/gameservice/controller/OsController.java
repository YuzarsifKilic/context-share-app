package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.OsDto;
import com.yuzarsif.gameservice.service.OsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/os")
public class OsController {

    private final OsService osService;

    public OsController(OsService osService) {
        this.osService = osService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<OsDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(osService.findByNameContaining(name));
    }
}
