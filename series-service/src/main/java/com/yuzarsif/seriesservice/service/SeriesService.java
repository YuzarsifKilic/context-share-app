package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.SeriesDto;
import com.yuzarsif.seriesservice.dto.request.CreateSeriesRequest;
import com.yuzarsif.seriesservice.dto.request.SearchByList;
import com.yuzarsif.seriesservice.model.*;
import com.yuzarsif.seriesservice.repository.SeriesRepository;
import com.yuzarsif.seriesservice.utils.DateConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final StarService starService;
    private final DirectorService directorService;
    private final WriterService writerService;
    private final SeriesCategoryService seriesCategoryService;

    public SeriesService(SeriesRepository seriesRepository,
                         StarService starService,
                         DirectorService directorService,
                         WriterService writerService,
                         SeriesCategoryService seriesCategoryService) {
        this.seriesRepository = seriesRepository;
        this.starService = starService;
        this.directorService = directorService;
        this.writerService = writerService;
        this.seriesCategoryService = seriesCategoryService;
    }

    public SeriesDto createSeries(CreateSeriesRequest createSeriesRequest) {
        Set<Director> directors = createSeriesRequest.directors()
                .stream()
                .map(directorService::findById)
                .collect(Collectors.toSet());

        Set<Star> stars = createSeriesRequest.stars()
                .stream()
                .map(starService::findById)
                .collect(Collectors.toSet());

        Set<Writer> writers = createSeriesRequest.writers()
                .stream()
                .map(writerService::findById)
                .collect(Collectors.toSet());

        Set<SeriesCategory> seriesCategories = createSeriesRequest.categories()
                .stream()
                .map(seriesCategoryService::findById)
                .collect(Collectors.toSet());

        Series series = Series.builder()
                .name(createSeriesRequest.name())
                .imageUrl(createSeriesRequest.imageUrl())
                .releaseDate(DateConverter.convert(createSeriesRequest.releaseDate()))
                .isFinished(createSeriesRequest.isFinished())
                .mostPopular(createSeriesRequest.mostPopular())
                .seasonCount(createSeriesRequest.seasonCount())
                .episodeCount(createSeriesRequest.episodeCount())
                .storyLine(createSeriesRequest.storyLine())
                .language(createSeriesRequest.language())
                .directors(directors)
                .stars(stars)
                .writers(writers)
                .seriesCategories(seriesCategories)
                .build();

        Series savedSeries = seriesRepository.save(series);

        return SeriesDto.convert(savedSeries);
    }

    protected Series findById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Series not found with id: " + id));
    }

    public List<SeriesDto> getSeries(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return seriesRepository.findAll(pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByLanguage(Language language, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        // todo check the language exists

        return seriesRepository.findByLanguage(language, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByMostPopular(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return seriesRepository.findByMostPopular(true, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByLanguageAndMostPopular(Language language, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        // todo check the language exists

        return seriesRepository.findByLanguageAndMostPopular(language, true, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByName(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return seriesRepository.findByNameContaining(name, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesBySeriesCategoriesIn(SearchByList searchByList, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        // todo get categories from category service

        Collection<Set<SeriesCategory>> seriesCategoriesList = new ArrayList<>();

        return seriesRepository.findBySeriesCategoriesIn(seriesCategoriesList, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByStarsIn(Long starId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Star star = starService.findById(starId);

        Set<Star> starsList = new HashSet<>();
        starsList.add(star);

        return seriesRepository.findByStars(starsList, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByDirectorsIn(Long directorId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Director director = directorService.findById(directorId);

        Set<Director> directorsList = new HashSet<>();
        directorsList.add(director);

        return seriesRepository.findByDirectors(directorsList, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }

    public List<SeriesDto> getSeriesByWritersIn(Long writerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Writer writer = writerService.findById(writerId);

        Set<Writer> writersList = new HashSet<>();
        writersList.add(writer);

        return seriesRepository.findByWriters(writersList, pageable)
                .stream()
                .map(SeriesDto::convert)
                .toList();
    }
}
