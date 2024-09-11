package com.yuzarsif.contextshare.reviewservice.controller;

import com.yuzarsif.contextshare.reviewservice.dto.CreateReplyRequest;
import com.yuzarsif.contextshare.reviewservice.dto.CreateReviewRequest;
import com.yuzarsif.contextshare.reviewservice.dto.ReplyDto;
import com.yuzarsif.contextshare.reviewservice.dto.ReviewDto;
import com.yuzarsif.contextshare.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{contextType}/{contextId}")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable String contextType, @PathVariable Long contextId) {
        return ResponseEntity.ok(reviewService.getReviews(contextType, contextId));
    }

    @PostMapping("/{reviewId}/replies")
    public ResponseEntity<String> createReply(@PathVariable String reviewId, @RequestBody CreateReplyRequest request) {
        reviewService.reply(reviewId, request);
        return ResponseEntity.ok("Reply created successfully");
    }

    @GetMapping("/{reviewId}/replies")
    public ResponseEntity<List<ReplyDto>> getReplies(@PathVariable String reviewId) {
        return ResponseEntity.ok(reviewService.getReplies(reviewId));
    }
}
