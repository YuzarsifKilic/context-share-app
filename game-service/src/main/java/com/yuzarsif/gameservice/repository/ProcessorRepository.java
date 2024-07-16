package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Processor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessorRepository extends JpaRepository<Processor, Long> {

    List<Processor> findByBrandContainingOrVersionContaining(String brand, String version);
}
