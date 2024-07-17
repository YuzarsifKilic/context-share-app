package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.ProcessorDto;
import com.yuzarsif.gameservice.dto.request.CreateProcessorRequest;
import com.yuzarsif.gameservice.service.ProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processors")
public class ProcessorController {

    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateProcessorRequest request) {
        processorService.create(request);
        return ResponseEntity.ok("Processor created successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProcessorDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(processorService.findByName(name));
    }
}
