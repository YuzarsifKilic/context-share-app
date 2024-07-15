package com.yuzarsif.movieservice.service;

import com.yuzarsif.movieservice.dto.MovieDto;
import com.yuzarsif.movieservice.model.Director;
import com.yuzarsif.movieservice.model.MovieCategory;
import com.yuzarsif.movieservice.model.Star;
import com.yuzarsif.movieservice.model.Writer;
import com.yuzarsif.movieservice.repository.MovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieCategoryService movieCategoryService;
    private final DirectorService directorService;
    private final StarService starService;

    private final WriterService writerService;

    public MovieService(MovieRepository movieRepository,
                        MovieCategoryService movieCategoryService,
                        DirectorService directorService,
                        StarService starService,
                        WriterService writerService) {
        this.movieRepository = movieRepository;
        this.movieCategoryService = movieCategoryService;
        this.directorService = directorService;
        this.starService = starService;
        this.writerService = writerService;
    }

    public List<MovieDto> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return movieRepository.findAll(pageable)
                .stream()
                .map(MovieDto::convert)
                .collect(Collectors.toList());
    }

    public List<MovieDto> findMoviesByName(String name) {
        return movieRepository.findByNameContaining(name)
                .stream()
                .map(MovieDto::convert)
                .collect(Collectors.toList());
    }

    public List<MovieDto> findMoviesByCategory(String category) {
        MovieCategory movieCategory = movieCategoryService.findByName(category);

        return movieRepository.findByCategories(Set.of(movieCategory))
                .stream()
                .map(MovieDto::convert)
                .collect(Collectors.toList());
    }

    public List<MovieDto> findMoviesByDirector(Long directorId) {
        Director director = directorService.findById(directorId);

        return movieRepository.findByDirectors(Set.of(director))
                .stream()
                .map(MovieDto::convert)
                .collect(Collectors.toList());
    }

    public List<MovieDto> findMoviesByStar(Long starId) {
        Star star = starService.findById(starId);

        return movieRepository.findByStars(Set.of(star))
                .stream()
                .map(MovieDto::convert)
                .collect(Collectors.toList());
    }

    public List<MovieDto> findMoviesByWriter(Long writerId) {
        Writer writer = writerService.findById(writerId);

        return movieRepository.findByWriters(Set.of(writer))
                .stream()
                .map(MovieDto::convert)
                .collect(Collectors.toList());
    }
}
