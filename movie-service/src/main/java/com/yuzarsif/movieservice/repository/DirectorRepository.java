package com.yuzarsif.movieservice.repository;

import com.yuzarsif.movieservice.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
