package com.yuzarsif.contextshare.reviewservice.service;

import com.yuzarsif.contextshare.reviewservice.clients.GameClient;
import com.yuzarsif.contextshare.reviewservice.clients.user.UserClient;
import com.yuzarsif.contextshare.reviewservice.clients.user.UserResponse;
import com.yuzarsif.contextshare.reviewservice.dto.CreateReviewRequest;
import com.yuzarsif.contextshare.reviewservice.dto.ReviewDto;
import com.yuzarsif.contextshare.reviewservice.exception.ClientResponseException;
import com.yuzarsif.contextshare.reviewservice.exception.UserException;
import com.yuzarsif.contextshare.reviewservice.model.ContextType;
import com.yuzarsif.contextshare.reviewservice.model.Review;
import com.yuzarsif.contextshare.reviewservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserClient userClient;
    private final GameClient gameClient;

    public void createReview(CreateReviewRequest createReviewRequest) {
        // TODO: check if user exists
        Boolean userExist = userClient.checkUserExist(createReviewRequest.userId());
        if (!userExist) {
            throw new UserException("User not found with id: " + createReviewRequest.userId());
        }
        // TODO: check if context exists
        if (createReviewRequest.contextType() == ContextType.GAME) {
            Boolean gameExist = gameClient.checkGameExist(createReviewRequest.contextId());
            if (!gameExist) {
                throw new ClientResponseException("Game not found with id: " + createReviewRequest.contextId());
            }
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
    }

    public List<ReviewDto> getReviews(String contextType, Long contextId) {
        // TODO: check if context exists
        if (Objects.equals(contextType, ContextType.GAME.name())) {
            Boolean gameExist = gameClient.checkGameExist(contextId);
            if (!gameExist) {
                throw new ClientResponseException("Game not found with id: " + contextId);
            }
        }
        // TODO: get user each context
        List<String> userIds = new ArrayList<>();
        List<Review> byContextIdAndContextType = reviewRepository.findByContextIdAndContextType(contextId, ContextType.valueOf(contextType));
        byContextIdAndContextType
                .forEach(review -> userIds.add(review.getUserId()));
        List<UserResponse> usersByIdList = userClient.findUsersByIdList(userIds);
        // TODO: create reviewdto with user fields
        List<ReviewDto> reviewDtoList = IntStream.range(0, byContextIdAndContextType.size())
                .mapToObj(i -> ReviewDto.convert(byContextIdAndContextType.get(i), usersByIdList.get(i)))
                .toList();
        return reviewDtoList;
    }
}
