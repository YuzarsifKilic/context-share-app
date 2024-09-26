package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.client.epic.response.ProductConfigurationResponse;
import com.yuzarsif.gameservice.dto.request.CreateSystemRequirementRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.*;
import com.yuzarsif.gameservice.repository.SystemRequirementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemRequirementService {

    private final SystemRequirementRepository systemRequirementRepository;
    private final OsService osService;
    private final ProcessorService processorService;
    private final GraphicsService graphicsService;
    private final MemoryService memoryService;
    private final StorageService storageService;

    public SystemRequirement findById(Long id) {
        return systemRequirementRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SystemRequirement not found with id " + id));
    }

    public void create(CreateSystemRequirementRequest request) {
        Os os = osService.findById(request.osId());
        Set<Processor> processor = processorService.findByIdList(request.processorIdList());
        Set<Graphics> graphicsList = graphicsService.findByIdList(request.graphicsIdList());

        // TODO: find memory and storage

        SystemRequirement systemRequirement = SystemRequirement
                .builder()
                //.memory(request.memory())
                .os(os)
                .processors(processor)
                .graphics(graphicsList)
                //.storage(request.storage())
                .build();

        systemRequirementRepository.save(systemRequirement);
    }

    public SystemRequirement extractSystemRequirements(String systemRequirements) {
        List<String> requirements = Arrays.stream(systemRequirements.split("<li>")).toList();
        Os os = null;
        Set<Processor> processors = new HashSet<>();
        Set<Graphics> graphics = new HashSet<>();
        Memory memory = null;
        Storage storage = null;

        for (String requirement: requirements) {
            String clearedRequirement = clearData(requirement);
            if (clearedRequirement.startsWith("OS")) {
                os = osService.getOs(clearedRequirement);
            } if (clearedRequirement.startsWith("Processor:")) {
                processors = new HashSet<>(processorService.getProcessor(clearedRequirement));
            } if (clearedRequirement.startsWith("Graphics:")) {
                graphics = new HashSet<>(graphicsService.getGraphics(clearedRequirement));
            } if (clearedRequirement.startsWith("Storage:")) {
                clearedRequirement = clearedRequirement.substring(9);
                storage = storageService.clearData(clearedRequirement);
            } if (clearedRequirement.startsWith("Memory:")) {
                clearedRequirement = clearedRequirement.substring(8);
                memory = memoryService.clearData(clearedRequirement);
            }
        }

        SystemRequirement systemRequirement = new SystemRequirement();
        // That's why too much null value in db
        // TODO: Delete this
//        systemRequirement.setOs(null);
//        systemRequirement.setProcessors(null);
//        systemRequirement.setGraphics(null);
//        systemRequirement.setStorage(null);
//        systemRequirement.setMemory(null);

        //TODO: find memory and storage

        if (os != null) {
            systemRequirement.setOs(os);
        }
        if (!processors.isEmpty()) {
            log.info(processors.toString());
            systemRequirement.setProcessors(processors);
        }
        if (!graphics.isEmpty()) {
            systemRequirement.setGraphics(graphics);
        }
        if (storage != null) {
            systemRequirement.setStorage(storage);
        }
        if (memory != null) {
            systemRequirement.setMemory(memory);
        }

        return systemRequirementRepository.save(systemRequirement);
    }

    private String clearData(String input) {
        List<String> invalidKeys = List.of("<h1>", "</h1>", "<p>", "</p>", "<br>", "<ul>", "</ul>", "<li>", "</li>", "<strong>", "</strong>", "<a>", "</a>", "<h2>", "</h2>", "<i>", "</i>", "<ul class=\"bb_ul\">");
        for (String invalidKey: invalidKeys) {
            input = input.replace(invalidKey, "");
        }
        return input;
    }

    public SystemRequirement extractMinSystemRequirementsEpic(ArrayList<ProductConfigurationResponse.Window> windows) {
        Os os = null;
        Set<Processor> processors = new HashSet<>();
        Set<Graphics> graphics = new HashSet<>();
        Memory memory = null;
        Storage storage = null;

        for (ProductConfigurationResponse.Window window: windows) {
            if (window.title.equals("OS version") && window.minimum != null) {
                os = osService.getOs(window.minimum);
            } if (window.title.equals("CPU") && window.minimum != null) {
                processors = processorService.extractProcessorForEpicGames(window.minimum);
            } if (window.title.equals("GPU") && window.minimum != null) {
                graphics = graphicsService.extractGraphicsForEpicGames(window.minimum);
            } if (window.title.equals("Memory") && window.minimum != null) {
                if (memoryService.clearData(window.minimum) != null) {
                    memory = memoryService.clearData(window.minimum);
                }
            } if (window.title.equals("Storage") && window.minimum != null) {
                if (storageService.clearData(window.minimum) != null) {
                    storage = storageService.clearData(window.minimum);
                }
            }
        }
        SystemRequirement systemRequirement = new SystemRequirement();
        systemRequirement.setOs(os);
        systemRequirement.setProcessors(processors);
        systemRequirement.setGraphics(graphics);
        systemRequirement.setStorage(storage);
        systemRequirement.setMemory(memory);
        return systemRequirementRepository.save(systemRequirement);
    }

    public SystemRequirement extractRecommendedSystemRequirementsEpic(ArrayList<ProductConfigurationResponse.Window> windows) {
        Os os = null;
        Set<Processor> processors = new HashSet<>();
        Set<Graphics> graphics = new HashSet<>();
        Memory memory = null;
        Storage storage = null;

        for (ProductConfigurationResponse.Window window: windows) {
            if (window.title.equals("OS version") && window.recommended != null) {
                os = osService.getOs(window.recommended);
            } if (window.title.equals("CPU") && window.recommended != null) {
                processors = processorService.extractProcessorForEpicGames(window.recommended);
            } if (window.title.equals("GPU") && window.recommended != null) {
                graphics = graphicsService.extractGraphicsForEpicGames(window.recommended);
            } if (window.title.equals("Memory") && window.recommended != null) {
                if (memoryService.clearData(window.recommended) != null) {
                    memory = memoryService.clearData(window.recommended);
                }
            } if (window.title.equals("Storage") && window.recommended != null) {
                if (storageService.clearData(window.recommended) != null) {
                    storage = storageService.clearData(window.recommended);
                }
            }
        }
        SystemRequirement systemRequirement = new SystemRequirement();
        if (os != null) {
            systemRequirement.setOs(os);
        }
        if (!processors.isEmpty()) {
            systemRequirement.setProcessors(processors);
        }
        if (!graphics.isEmpty()) {
            systemRequirement.setGraphics(graphics);
        }
        if (storage != null) {
            systemRequirement.setStorage(storage);
        }
        if (memory != null) {
            systemRequirement.setMemory(memory);
        }
        return systemRequirementRepository.save(systemRequirement);

    }

}
