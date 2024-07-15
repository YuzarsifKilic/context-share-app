package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Language;
import com.yuzarsif.gameservice.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language findById(Long id) {
        return languageRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Language not found with id " + id));
    }

    public Set<Language> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }
}
