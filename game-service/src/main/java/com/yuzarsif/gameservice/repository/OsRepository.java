package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Os;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OsRepository extends JpaRepository<Os, Long> {

    List<Os> findByBrandContainingOrVersionContaining(String brand, String version);
}
