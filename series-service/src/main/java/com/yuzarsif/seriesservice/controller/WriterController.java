package com.yuzarsif.seriesservice.controller;

import com.yuzarsif.seriesservice.dto.WriterDto;
import com.yuzarsif.seriesservice.dto.request.CreateWriterRequest;
import com.yuzarsif.seriesservice.service.WriterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/writers")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }


    @GetMapping
    public ResponseEntity<List<WriterDto>> getWriters(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return ResponseEntity.ok(writerService.findWritersByName(name, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriterDto> getWriterById(@PathVariable Long id) {
        return ResponseEntity.ok(writerService.getWriterById(id));
    }

    @PostMapping
    public ResponseEntity<WriterDto> createWriter(@RequestBody CreateWriterRequest request) {
        return ResponseEntity.ok(writerService.createWriter(request));
    }
}
