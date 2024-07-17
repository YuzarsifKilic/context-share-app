package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.OsDto;
import com.yuzarsif.gameservice.dto.request.CreateOsRequest;
import com.yuzarsif.gameservice.service.OsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/os")
public class OsController {

    private final OsService osService;

    public OsController(OsService osService) {
        this.osService = osService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateOsRequest request) {
        osService.create(request);
        return ResponseEntity.ok("Os created successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<OsDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(osService.findByNameContaining(name));
    }
}
