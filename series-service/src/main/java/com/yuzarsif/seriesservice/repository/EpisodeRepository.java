package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findBySeason_Id(Long seasonId);
}
