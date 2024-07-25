package com.yuzarsif.seriesservice.repository;

import com.yuzarsif.seriesservice.model.Writer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WriterRepository extends JpaRepository<Writer, Long> {

    List<Writer> findByNameContaining(String name, Pageable pageable);
}
