package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreatePublisherRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Publisher;
import com.yuzarsif.gameservice.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher findById(Long id) {
        return publisherRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with id " + id));
    }

    public Set<Publisher> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }

    public Optional<Publisher> findByName(String name) {
        return publisherRepository
                .findByName(name);
    }

    public Publisher create(CreatePublisherRequest request) {
        Publisher publisher = Publisher
                .builder()
                .name(request.name())
                .build();

        return publisherRepository.save(publisher);
    }
}
