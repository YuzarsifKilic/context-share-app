package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.DirectorDto;
import com.yuzarsif.seriesservice.dto.request.CreateDirectorRequest;
import com.yuzarsif.seriesservice.model.Director;
import com.yuzarsif.seriesservice.repository.DirectorRepository;
import com.yuzarsif.seriesservice.utils.DateConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    protected Director findById(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Director not found with id: " + id));
    }

    public DirectorDto getDirectorById(Long id) {
        return DirectorDto.convert(findById(id));
    }

    public List<DirectorDto> getDirectors(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return directorRepository.findByNameContaining(name, pageable)
                .stream()
                .map(DirectorDto::convert)
                .toList();
    }

    public DirectorDto createDirector(CreateDirectorRequest request) {
        Director director = Director.builder()
                .name(request.name())
                .bio(request.bio())
                .imageUrl(request.imageUrl())
                .birthDate(DateConverter.convert(request.birthDate()))
                .build();

        Director savedDirector = directorRepository.save(director);

        return DirectorDto.convert(savedDirector);
    }
}
