package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
