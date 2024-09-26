package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.dto.GameListDto;
import com.yuzarsif.gameservice.model.Game;
import com.yuzarsif.gameservice.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findByNameContaining(String name, Pageable pageable);

    Page<Game> findByGenres(Set<Genre> genres, Pageable pageable);

    Optional<Game> findByName(String name);

    @Query("select new com.yuzarsif.gameservice.dto.GameListDto(g.id, g.name, g.mainImage) from Game g")
    Page<GameListDto> findAllGames(Pageable pageable);
}
