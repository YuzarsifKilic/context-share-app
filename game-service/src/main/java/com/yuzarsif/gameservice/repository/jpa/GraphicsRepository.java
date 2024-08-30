package com.yuzarsif.gameservice.repository.jpa;

import com.yuzarsif.gameservice.model.Graphics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GraphicsRepository extends JpaRepository<Graphics, Long> {

    List<Graphics> findByBrandContainingOrVersionContaining(String brand, String version);

    Optional<Graphics> findByBrandAndVersionAndDescription(String brand, String version, String description);
}
