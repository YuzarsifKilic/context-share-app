package com.yuzarsif.contextshare.reviewservice.repository;

import com.yuzarsif.contextshare.reviewservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByContextId(Long contextId);
}
