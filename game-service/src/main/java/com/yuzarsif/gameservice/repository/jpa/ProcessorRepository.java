package com.yuzarsif.gameservice.repository.jpa;

import com.yuzarsif.gameservice.model.Processor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessorRepository extends JpaRepository<Processor, Long> {

    List<Processor> findByBrandContainingOrVersionContaining(String brand, String version);

    Optional<Processor> findByBrandAndVersionAndDescription(String brand, String version, String description);
}
