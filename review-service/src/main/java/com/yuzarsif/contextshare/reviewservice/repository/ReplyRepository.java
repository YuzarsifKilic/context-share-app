package com.yuzarsif.contextshare.reviewservice.repository;

import com.yuzarsif.contextshare.reviewservice.model.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReplyRepository extends MongoRepository<Reply, String> {
}
