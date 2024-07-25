package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Director;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    List<Director> findByNameContaining(String name, Pageable pageable);
}
