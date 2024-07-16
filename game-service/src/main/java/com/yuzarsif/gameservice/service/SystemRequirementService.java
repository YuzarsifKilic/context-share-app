package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreateSystemRequirementRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Graphics;
import com.yuzarsif.gameservice.model.Os;
import com.yuzarsif.gameservice.model.Processor;
import com.yuzarsif.gameservice.model.SystemRequirement;
import com.yuzarsif.gameservice.repository.SystemRequirementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SystemRequirementService {

    private final SystemRequirementRepository systemRequirementRepository;
    private final OsService osService;
    private final ProcessorService processorService;
    private final GraphicsService graphicsService;

    public SystemRequirementService(SystemRequirementRepository systemRequirementRepository, OsService osService, ProcessorService processorService, GraphicsService graphicsService) {
        this.systemRequirementRepository = systemRequirementRepository;
        this.osService = osService;
        this.processorService = processorService;
        this.graphicsService = graphicsService;
    }

    public SystemRequirement findById(Long id) {
        return systemRequirementRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SystemRequirement not found with id " + id));
    }

    public void create(CreateSystemRequirementRequest request) {
        Os os = osService.findById(request.osId());
        Processor processor = processorService.findById(request.processorId());
        Set<Graphics> graphicsList = graphicsService.findByIdList(request.graphicsIdList());

        SystemRequirement systemRequirement = SystemRequirement
                .builder()
                .memory(request.memory())
                .os(os)
                .processor(processor)
                .graphics(graphicsList)
                .storage(request.storage())
                .build();

        systemRequirementRepository.save(systemRequirement);
    }
}
