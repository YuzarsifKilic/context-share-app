package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Platform;
import com.yuzarsif.gameservice.repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public Platform findById(Long id) {
        return platformRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Platform not found with id " + id));
    }

    public Set<Platform> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }
}
