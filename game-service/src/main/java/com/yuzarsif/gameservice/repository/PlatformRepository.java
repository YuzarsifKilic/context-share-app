package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findByName(String name);
}
