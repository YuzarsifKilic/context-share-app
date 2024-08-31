package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreateFeatureRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Feature;
import com.yuzarsif.gameservice.repository.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public Feature findById(Long id) {
        return featureRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feature not found with id " + id));
    }

    public Optional<Feature> findByName(String name) {
        return featureRepository
                .findByName(name);
    }

    public Set<Feature> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }

    public Feature create(CreateFeatureRequest request) {
        Feature feature = Feature
                .builder()
                .name(request.name())
                .build();

        return featureRepository.save(feature);
    }
}
