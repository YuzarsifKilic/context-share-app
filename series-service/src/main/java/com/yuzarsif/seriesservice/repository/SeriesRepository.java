package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    Page<Series> findByLanguage(Language language, Pageable pageable);

    Page<Series> findByMostPopular(Boolean mostPopular, Pageable pageable);

    Page<Series> findByLanguageAndMostPopular(Language language, Boolean mostPopular, Pageable pageable);

    Page<Series> findByNameContaining(String name, Pageable pageable);

    Page<Series> findBySeriesCategoriesIn(Collection<Set<SeriesCategory>> seriesCategories, Pageable pageable);

    Page<Series> findByStars(Set<Star> stars, Pageable pageable);

    Page<Series> findByDirectors(Set<Director> directors, Pageable pageable);

    Page<Series> findByWriters(Set<Writer> writers, Pageable pageable);
}
