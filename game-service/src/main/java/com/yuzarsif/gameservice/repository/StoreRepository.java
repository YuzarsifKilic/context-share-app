package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
