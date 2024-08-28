package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.CheckedGame;
import com.yuzarsif.gameservice.model.Language;
import com.yuzarsif.gameservice.repository.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public Set<Language> extractAudioLanguageList(String languages, CheckedGame checkedGame) {
        if (languages.contains("<br><strong>*</strong>languages with full audio support")) {
            languages = languages.replace("<br><strong>*</strong>languages with full audio support", "");
        }
        List<Language> languageList = new ArrayList<>();
        String[] languageArray = languages.split(",");
        for (String language : languageArray) {
            if (language.contains("<strong>*</strong>")) {
                language = language.replace("<strong>*</strong>", "");
                if (language.startsWith(" ")) {
                    language = language.replaceFirst(" ", "");
                }
                languageList.add(ifLanguageExistsGetLanguageOrCreate(language));
            }
        }
        if (languageList.isEmpty()) {
            checkedGame.setAudioLanguageEmpty(true);
        }
        log.info("audio language list: " + languageList);
        return languageList.stream().collect(Collectors.toSet());
    }

    public Set<Language> extractSubtitleLanguageList(String languages, CheckedGame checkedGame) {
        if (languages.contains("<br><strong>*</strong>languages with full audio support")) {
            languages = languages.replace("<br><strong>*</strong>languages with full audio support", "");
        }
        List<Language> languageList = new ArrayList<>();
        String[] languageArray = languages.split(",");
        for (String language : languageArray) {
            if (!language.contains("<strong>*</strong>")) {
                languageList.add(ifLanguageExistsGetLanguageOrCreate(language));
            }
        }
        if (languageList.isEmpty()) {
            checkedGame.setSubtitleLanguageEmpty(true);
        }
        log.info("subtitle language list: " + languageList);
        return languageList.stream().collect(Collectors.toSet());
    }

    private Language ifLanguageExistsGetLanguageOrCreate(String language) {
        return languageRepository
                .findByName(language)
                .orElseGet(() -> {
                    Language newLanguage = Language
                            .builder()
                            .name(language)
                            .build();
                    return languageRepository.save(newLanguage);
                });
    }
}
