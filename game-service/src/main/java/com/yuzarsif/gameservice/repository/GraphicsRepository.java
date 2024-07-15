package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Graphics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphicsRepository extends JpaRepository<Graphics, Long> {
}
