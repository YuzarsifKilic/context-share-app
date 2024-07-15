package com.yuzarsif.movieservice.repository;

import com.yuzarsif.movieservice.model.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieCategoryRepository extends JpaRepository<MovieCategory, Long> {

    Optional<MovieCategory> findByName(String name);
}
