package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    List<Season> findBySeries_Id(Long seriesId);
}
