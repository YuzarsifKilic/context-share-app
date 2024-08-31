package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.model.CheckedGame;
import com.yuzarsif.gameservice.repository.CheckedGameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckedGameService {

    private final CheckedGameRepository checkedGameRepository;

    public Boolean existsByGameId(Long gameId) {
        return checkedGameRepository.existsByGameId(gameId);
    }

    public void save(CheckedGame checkedGame) {
        CheckedGame save = checkedGameRepository.save(checkedGame);
        log.info("Checked game saved: " + save);
    }
}
