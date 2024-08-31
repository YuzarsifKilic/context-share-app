package com.yuzarsif.contextshare.reviewservice.controller;

import com.yuzarsif.contextshare.reviewservice.dto.CreateReviewRequest;
import com.yuzarsif.contextshare.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody CreateReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.ok("Review created successfully");
    }
}
