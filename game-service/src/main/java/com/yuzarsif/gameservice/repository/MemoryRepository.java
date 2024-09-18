package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

    Optional<Memory> findBySizeAndDescription(Integer size, String description);
}
