package com.yuzarsif.gameservice.repository.mongodb;

import com.yuzarsif.gameservice.model.CheckedGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckedGameRepository extends MongoRepository<CheckedGame, Long> {

    Boolean existsByGameId(Long gameId);
}
