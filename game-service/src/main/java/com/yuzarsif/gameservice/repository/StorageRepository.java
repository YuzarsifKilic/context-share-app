package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    Optional<Storage> findBySizeAndDescription(Integer size, String description);
}
