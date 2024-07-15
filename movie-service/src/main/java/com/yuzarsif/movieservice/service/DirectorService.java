package com.yuzarsif.movieservice.service;

import com.yuzarsif.movieservice.dto.CreateDirectorRequest;
import com.yuzarsif.movieservice.exception.EntityNotFoundException;
import com.yuzarsif.movieservice.model.Director;
import com.yuzarsif.movieservice.repository.DirectorRepository;
import com.yuzarsif.movieservice.utils.DateConverter;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    protected Director findById(Long directorId) {
        return directorRepository.findById(directorId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Director with id %s not found", directorId)));
    }

    public void save(CreateDirectorRequest request){
        Director director = Director
                .builder()
                .bio(request.bio())
                .birthDate(DateConverter.convert(request.birthDate()))
                .imageUrl(request.imageUrl())
                .name(request.name())
                .build();

        directorRepository.save(director);
    }

    public void delete(Long directorId){
        findById(directorId);
        directorRepository.deleteById(directorId);
    }
}
