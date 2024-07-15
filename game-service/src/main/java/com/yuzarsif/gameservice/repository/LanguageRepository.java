package com.yuzarsif.gameservice.repository;

import com.yuzarsif.gameservice.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
