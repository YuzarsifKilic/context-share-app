package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByName(String name);
}
