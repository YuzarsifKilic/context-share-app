package com.yuzarsif.contextshare.reviewservice.service;

import com.yuzarsif.contextshare.reviewservice.clients.game.GameClient;
import com.yuzarsif.contextshare.reviewservice.clients.game.GameListDto;
import com.yuzarsif.contextshare.reviewservice.clients.user.UserClient;
import com.yuzarsif.contextshare.reviewservice.clients.user.UserResponse;
import com.yuzarsif.contextshare.reviewservice.dto.CreateReplyRequest;
import com.yuzarsif.contextshare.reviewservice.dto.CreateReviewRequest;
import com.yuzarsif.contextshare.reviewservice.dto.ReplyDto;
import com.yuzarsif.contextshare.reviewservice.dto.ReviewDto;
import com.yuzarsif.contextshare.reviewservice.exception.ClientResponseException;
import com.yuzarsif.contextshare.reviewservice.exception.ReviewNotFoundException;
import com.yuzarsif.contextshare.reviewservice.exception.UserException;
import com.yuzarsif.contextshare.reviewservice.kafka.ReplyProducer;
import com.yuzarsif.contextshare.reviewservice.kafka.ReviewNotification;
import com.yuzarsif.contextshare.reviewservice.model.ContextType;
import com.yuzarsif.contextshare.reviewservice.model.Reply;
import com.yuzarsif.contextshare.reviewservice.model.Review;
import com.yuzarsif.contextshare.reviewservice.repository.ReplyRepository;
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
    private final ReplyRepository replyRepository;
    private final ReplyProducer replyProducer;

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

            gameClient.addComment(createReviewRequest.contextId());
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
                .forEach(review -> {
                    userIds.add(review.getUserId());
                });
        List<UserResponse> usersByIdList = userClient.findUsersByIdList(userIds);
        // TODO: create reviewdto with user fields
        List<ReviewDto> reviewDtoList = IntStream.range(0, byContextIdAndContextType.size())
                .mapToObj(i -> ReviewDto.convert(byContextIdAndContextType.get(i), usersByIdList.get(i)))
                .toList();
        return reviewDtoList;
    }

    public void reply(String reviewId, CreateReplyRequest request) {
        // TODO: check if review exists
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        String contextName = "";

        // TODO: get context from review
        if (review.getContextType() == ContextType.GAME) {
            GameListDto gameInfo = gameClient.getGameInfo(review.getContextId());
            contextName = gameInfo.name();
        }

        // TODO: check if user exists
        Boolean userExist = userClient.checkUserExist(request.userId());
        if (!userExist) {
            throw new UserException("User not found with id: " + request.userId());
        }

        // TODO: get user
        UserResponse userResponse = userClient.findUserById(request.userId());

        Reply reply = Reply
                .builder()
                .userId(request.userId())
                .comment(request.comment())
                .build();

        Reply savedReply = replyRepository.save(reply);

        if (review.getReplies() == null) {
            List<Reply> replies = new ArrayList<>();
            replies.add(savedReply);
            review.setReplies(replies);
        } else {
            review.getReplies().add(reply);
        }

        Review savedReview = reviewRepository.save(review);

        gameClient.addComment(review.getContextId());

        // TODO: send notification
        replyProducer.produceReviewNotification(new ReviewNotification(
                userResponse.email(),
                userResponse.firstName(),
                userResponse.lastName(),
                contextName,
                savedReview.getComment()));
    }

    public List<ReplyDto> getReplies(String reviewId) {
        // TODO: check if review exists
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));

        List<Reply> replies = review.getReplies();

        List<String> userIds = replies.stream()
                .map(Reply::getUserId)
                .toList();

        List<UserResponse> usersByIdList = userClient.findUsersByIdList(userIds);

        List<ReplyDto> replyDtoList = IntStream.range(0, replies.size())
                .mapToObj(i -> ReplyDto.convert(replies.get(i), usersByIdList.get(i)))
                .toList();

        return replyDtoList;
    }
}
