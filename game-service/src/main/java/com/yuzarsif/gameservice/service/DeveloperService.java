package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreateDeveloperRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Developer;
import com.yuzarsif.gameservice.repository.jpa.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public Developer create(CreateDeveloperRequest request) {
        Developer developer = Developer
                .builder()
                .name(request.name())
                .build();

        return developerRepository.save(developer);
    }

    public Optional<Developer> findByName(String name) {
        return developerRepository
                .findByName(name);
    }


    public Developer findById(Long id) {
        return developerRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Developer not found with id " + id));
    }

    public Set<Developer> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }
}
