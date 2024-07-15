package com.yuzarsif.movieservice.repository;

import com.yuzarsif.movieservice.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long> {
}
