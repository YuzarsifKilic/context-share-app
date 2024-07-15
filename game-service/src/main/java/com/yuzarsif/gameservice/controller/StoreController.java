package com.yuzarsif.gameservice.controller;

import com.yuzarsif.gameservice.dto.request.CreateDiscountRequest;
import com.yuzarsif.gameservice.dto.request.CreateStoreRequest;
import com.yuzarsif.gameservice.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<String> createStore(@RequestBody CreateStoreRequest createStoreRequest) {
        storeService.createStore(createStoreRequest);
        return ResponseEntity.ok("Store created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> createDiscount(@PathVariable Long id, @RequestBody CreateDiscountRequest createDiscountRequest) {
        storeService.createDiscount(id, createDiscountRequest);
        return ResponseEntity.ok("Discount created successfully");
    }
}
