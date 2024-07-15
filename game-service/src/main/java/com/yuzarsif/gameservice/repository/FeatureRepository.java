package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
