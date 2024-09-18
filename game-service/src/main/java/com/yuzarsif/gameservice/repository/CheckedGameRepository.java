package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.CheckedGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckedGameRepository extends MongoRepository<CheckedGame, Long> {

    Boolean existsByGameId(String gameId);
}
