package com.yuzarsif.gameservice.repository.jpa;

import com.yuzarsif.gameservice.model.Os;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OsRepository extends JpaRepository<Os, Long> {

    List<Os> findByBrandContainingOrVersionContaining(String brand, String version);

    Optional<Os> findByBrandAndVersionAndDescription(String brand, String version, String description);
}
