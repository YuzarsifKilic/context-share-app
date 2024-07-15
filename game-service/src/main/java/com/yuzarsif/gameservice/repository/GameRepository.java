package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Game;
import com.yuzarsif.gameservice.model.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByNameContaining(String name, Pageable pageable);

    List<Game> findByGenres(Set<Genre> genres, Pageable pageable);
}
