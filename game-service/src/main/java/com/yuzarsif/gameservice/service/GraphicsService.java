package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Graphics;
import com.yuzarsif.gameservice.repository.GraphicsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Graphics> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .toList();
    }
}
