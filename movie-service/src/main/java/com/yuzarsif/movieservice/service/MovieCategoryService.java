package com.yuzarsif.movieservice.service;

import com.yuzarsif.movieservice.exception.EntityNotFoundException;
import com.yuzarsif.movieservice.model.MovieCategory;
import com.yuzarsif.movieservice.repository.MovieCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieCategoryService {

    private final MovieCategoryRepository movieCategoryRepository;

    public MovieCategoryService(MovieCategoryRepository movieCategoryRepository) {
        this.movieCategoryRepository = movieCategoryRepository;
    }

    protected MovieCategory findByName(String name) {
        return movieCategoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Movie category with name %s not found", name)));
    }
}
