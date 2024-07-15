package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
