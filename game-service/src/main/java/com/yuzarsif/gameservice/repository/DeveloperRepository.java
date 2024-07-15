package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
