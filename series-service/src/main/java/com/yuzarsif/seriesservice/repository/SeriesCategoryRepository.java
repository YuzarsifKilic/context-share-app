package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.SeriesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesCategoryRepository extends JpaRepository<SeriesCategory, Long> {
}
