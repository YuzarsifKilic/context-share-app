package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.repository.SeasonRepository;
import org.springframework.stereotype.Service;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }
}
