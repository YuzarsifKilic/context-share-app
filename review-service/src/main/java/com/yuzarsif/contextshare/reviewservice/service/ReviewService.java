package com.yuzarsif.contextshare.reviewservice.service;

import com.yuzarsif.contextshare.reviewservice.dto.CreateReviewRequest;
import com.yuzarsif.contextshare.reviewservice.model.Review;
import com.yuzarsif.contextshare.reviewservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void createReview(CreateReviewRequest createReviewRequest) {
        // TODO: check if user exists
        // TODO: check if context exists
        Review review = Review
                .builder()
                .userId(createReviewRequest.userId())
                .comment(createReviewRequest.comment())
                .contextId(createReviewRequest.contextId())
                .rating(createReviewRequest.rating())
                .contextType(createReviewRequest.contextType())
                .build();

        reviewRepository.save(review);
    }

    public List<Review> findByContextId(Long contextId) {
        // TODO: check if context exists
        // TODO: get user each context
        // TODO: create reviewdto with user fields
        return reviewRepository.findByContextId(contextId);
    }
}
