package com.yuzarsif.movieservice.controller;

import com.yuzarsif.movieservice.dto.CreateWriterRequest;
import com.yuzarsif.movieservice.service.WriterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/writers")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @PostMapping
    public ResponseEntity<String> saveWriter(@RequestBody CreateWriterRequest request){
        writerService.save(request);
        return ResponseEntity.ok("Writer created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWriter(@PathVariable Long id){
        writerService.delete(id);
        return ResponseEntity.ok("Writer deleted successfully");
    }
}
