package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);
}
