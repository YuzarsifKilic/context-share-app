package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.GameDto;
import com.yuzarsif.gameservice.dto.request.SearchByGenreRequest;
import com.yuzarsif.gameservice.exception.GameNotFoundException;
import com.yuzarsif.gameservice.model.Game;
import com.yuzarsif.gameservice.repository.GameRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameDto> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return gameRepository.findAll(pageable)
                .stream()
                .map(GameDto::convert)
                .collect(Collectors.toList());
    }

    public List<GameDto> findByNameContaining(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return gameRepository.findByNameContaining(name, pageable)
                .stream()
                .map(GameDto::convert)
                .collect(Collectors.toList());
    }

    public List<GameDto> findByGenres(SearchByGenreRequest searchByGenre) {
        Pageable pageable = PageRequest.of(searchByGenre.page(), searchByGenre.size());

        return gameRepository.findByGenres(searchByGenre.genres(), pageable)
                .stream()
                .map(GameDto::convert)
                .collect(Collectors.toList());
    }

    protected Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id " + id));
    }
}
