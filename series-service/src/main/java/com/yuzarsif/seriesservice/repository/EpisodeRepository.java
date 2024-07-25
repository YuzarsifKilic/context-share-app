package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
