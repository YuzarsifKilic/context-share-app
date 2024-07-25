package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.SeriesDto;
import com.yuzarsif.seriesservice.dto.request.SearchByList;
import com.yuzarsif.seriesservice.model.*;
import com.yuzarsif.seriesservice.repository.SeriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final StarService starService;
    private final DirectorService directorService;
    private final WriterService writerService;

    public SeriesService(SeriesRepository seriesRepository, StarService starService, DirectorService directorService, WriterService writerService) {
        this.seriesRepository = seriesRepository;
        this.starService = starService;
        this.directorService = directorService;
        this.writerService = writerService;
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
