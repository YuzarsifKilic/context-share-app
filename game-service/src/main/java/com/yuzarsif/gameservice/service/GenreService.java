package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreateGenreRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Genre;
import com.yuzarsif.gameservice.repository.jpa.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findById(Long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id " + id));
    }

    public Optional<Genre> findByName(String name) {
        return genreRepository
                .findByName(name);
    }

    public Set<Genre> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }

    public Genre create(CreateGenreRequest request) {
        Genre genre = Genre
                .builder()
                .name(request.name())
                .build();

        return genreRepository.save(genre);
    }
}
