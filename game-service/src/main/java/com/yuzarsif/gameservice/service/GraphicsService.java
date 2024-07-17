package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.GraphicsDto;
import com.yuzarsif.gameservice.dto.request.CreateGraphicsRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Graphics;
import com.yuzarsif.gameservice.repository.GraphicsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphicsService {

    private final GraphicsRepository graphicsRepository;

    public GraphicsService(GraphicsRepository graphicsRepository) {
        this.graphicsRepository = graphicsRepository;
    }

    public Graphics findById(Long id) {
        return graphicsRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Graphics not found with id " + id));
    }

    public Set<Graphics> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }

    public List<GraphicsDto> findByName(String search) {
        return graphicsRepository
                .findByBrandContainingOrVersionContaining(search, search)
                .stream()
                .map(GraphicsDto::convert)
                .toList();
    }

    public void create(CreateGraphicsRequest request) {
        Graphics graphics = Graphics
                .builder()
                .brand(request.brand())
                .version(request.version())
                .build();

        graphicsRepository.save(graphics);
    }
}
