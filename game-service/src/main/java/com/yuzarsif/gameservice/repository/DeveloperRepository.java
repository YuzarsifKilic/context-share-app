package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByName(String name);
}
