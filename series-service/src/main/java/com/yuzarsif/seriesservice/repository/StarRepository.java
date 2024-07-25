package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Star;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long> {

    Page<Star> findByNameContaining(String name, Pageable pageable);
}
