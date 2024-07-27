package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.SeasonDto;
import com.yuzarsif.seriesservice.dto.request.CreateSeasonRequest;
import com.yuzarsif.seriesservice.model.Season;
import com.yuzarsif.seriesservice.model.Series;
import com.yuzarsif.seriesservice.repository.SeasonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeriesService seriesService;

    public SeasonService(SeasonRepository seasonRepository, SeriesService seriesService) {
        this.seasonRepository = seasonRepository;
        this.seriesService = seriesService;
    }

    public SeasonDto getSeason(Long id) {
        Season season = findById(id);
        return SeasonDto.convert(season);
    }

    public List<SeasonDto> getSeasonsBySeriesId(Long seriesId) {
        Series series = seriesService.findById(seriesId);

        return seasonRepository.findBySeries_Id(seriesId)
                .stream()
                .map(SeasonDto::convert)
                .toList();
    }

    protected Season findById(Long id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Season not found with id: " + id));
    }

    public SeasonDto createSeason(Long seriesId, CreateSeasonRequest request) {
        Series series = seriesService.findById(seriesId);

        Season season = Season.builder()
                .series(series)
                .seasonNumber(request.seasonNumber())
                .url(request.url())
                .episodeCount(request.episodeCount())
                .mostPopular(request.mostPopular())
                .storyLine(request.storyLine())
                .build();

        Season savedSeason = seasonRepository.save(season);

        return SeasonDto.convert(savedSeason);
    }
}
