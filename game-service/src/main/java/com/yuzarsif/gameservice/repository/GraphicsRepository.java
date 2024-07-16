package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Graphics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraphicsRepository extends JpaRepository<Graphics, Long> {

    List<Graphics> findByBrandContainingOrVersionContaining(String brand, String version);
}
