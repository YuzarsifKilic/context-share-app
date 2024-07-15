package com.yuzarsif.movieservice.repository;

import com.yuzarsif.movieservice.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {
}
