package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.ProcessorDto;
import com.yuzarsif.gameservice.service.ProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processors")
public class ProcessorController {

    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProcessorDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(processorService.findByName(name));
    }
}
