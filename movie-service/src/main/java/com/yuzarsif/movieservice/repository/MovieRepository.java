package com.yuzarsif.movieservice.repository;

import com.yuzarsif.movieservice.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByNameContaining(String name);

    List<Movie> findByCategories(Set<MovieCategory> category);

    List<Movie> findByDirectors(Set<Director> directors);

    List<Movie> findByStars(Set<Star> stars);

    List<Movie> findByWriters(Set<Writer> writers);
}
