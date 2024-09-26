package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Store;
import com.yuzarsif.gameservice.model.StoreType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Boolean existsByGame_NameAndStoreName(String name, StoreType storeName);
}
