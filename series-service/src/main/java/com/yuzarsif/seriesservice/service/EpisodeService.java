package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.EpisodeDto;
import com.yuzarsif.seriesservice.dto.request.CreateEpisodeRequest;
import com.yuzarsif.seriesservice.model.Episode;
import com.yuzarsif.seriesservice.model.Season;
import com.yuzarsif.seriesservice.repository.EpisodeRepository;
import com.yuzarsif.seriesservice.utils.DateConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final SeasonService seasonService;

    public EpisodeService(EpisodeRepository episodeRepository, SeasonService seasonService) {
        this.episodeRepository = episodeRepository;
        this.seasonService = seasonService;
    }

    public List<EpisodeDto> getEpisodesBySeasonId(Long seasonId) {
        Season season = seasonService.findById(seasonId);

        return episodeRepository.findBySeason_Id(seasonId)
                .stream()
                .map(EpisodeDto::convert)
                .toList();
    }

    public EpisodeDto getEpisode(Long id) {
        Episode episode = findById(id);
        return EpisodeDto.convert(episode);
    }

    protected Episode findById(Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + id));
    }



    public EpisodeDto createEpisode(Long seasonId, CreateEpisodeRequest request) {
        Season season = seasonService.findById(seasonId);

        Episode episode = Episode.builder()
                .season(season)
                .episodeNumber(request.episodeNumber())
                .name(request.name())
                .imageUrl(request.imageUrl())
                .episodeDate(DateConverter.convert(request.episodeDate()))
                .mostPopular(request.mostPopular())
                .storyLine(request.storyLine())
                .build();

        Episode savedEpisode = episodeRepository.save(episode);

        return EpisodeDto.convert(savedEpisode);
    }
}
