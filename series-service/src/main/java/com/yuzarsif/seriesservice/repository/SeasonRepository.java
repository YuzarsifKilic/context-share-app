package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
