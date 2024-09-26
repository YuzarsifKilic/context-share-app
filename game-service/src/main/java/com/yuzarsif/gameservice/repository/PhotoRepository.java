package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
