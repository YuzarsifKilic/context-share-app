package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }
}
