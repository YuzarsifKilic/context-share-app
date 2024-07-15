package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
