package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
