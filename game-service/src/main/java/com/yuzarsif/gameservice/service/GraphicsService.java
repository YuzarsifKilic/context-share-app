package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.GraphicsDto;
import com.yuzarsif.gameservice.dto.request.CreateGraphicsRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Graphics;
import com.yuzarsif.gameservice.repository.GraphicsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    protected List<Graphics> getGraphics(String graphics) {
        log.info("Graphics: " + graphics);
        List<Graphics> graphicsList = new ArrayList<>();

        //TODO: extract graphics add try catch block when get an error print a log

        graphics = graphics.substring(10);
        if (graphics.isEmpty() || graphics.length() > 60) {
            return graphicsList;
        }
        if (graphics.contains("Any") || graphics.length() < 5) {
            graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, graphics)));
            return graphicsList;
        }
        String[] graphicsArray = new String[2];
        if (graphics.contains("/")) {
            graphicsArray  = graphics.split("/", 2);
            return extractGraphics(graphicsArray);
        } else if (graphics.contains(" or ")) {
            graphicsArray  = graphics.split(" or ", 2);
            return extractGraphics(graphicsArray);
        } else if (graphics.contains(",")) {
            if (!graphics.contains(" ")) {
                graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, graphics)));
                return graphicsList;
            }
            String[] split = graphics.split(",", 2);
            if (!split[1].startsWith(" ")) {
                graphicsArray  = graphics.split(",", 2);
            } else {
                graphicsArray  = graphics.split(", ", 2);
            }
            return extractGraphics(graphicsArray);
        } else if (graphics.contains("|")) {
            graphicsArray  = graphics.split("\\|", 2);
            return extractGraphics(graphicsArray);
        } else {
            if (!graphics.contains(" ")) {
                graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, graphics)));
                return graphicsList;
            }
            String brand = graphics.substring(0, graphics.indexOf(" "));
            String version = graphics.substring(graphics.indexOf(" ")+1);
            graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(brand, version, null)));
            return graphicsList;
        }
    }

    private List<Graphics> extractGraphics(String[] graphics) {
        List<Graphics> graphicsList = new ArrayList<>();
        if (!graphics[0].contains(" ")) {
            graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, graphics[0])));
        } else {
            String firstGraphics = graphics[0];
            if (firstGraphics.startsWith(" ")) {
                firstGraphics = firstGraphics.replaceFirst(" ", "");
            }
            if (checkIsGraphicsBrandValid(firstGraphics)) {
                if (firstGraphics.contains(" ")) {
                    String firstGraphicsBrand = firstGraphics.substring(0, firstGraphics.indexOf(" "));
                    String firstGraphicsVersion = firstGraphics.substring(firstGraphics.indexOf(" ") + 1);
                    graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(firstGraphicsBrand, firstGraphicsVersion, null)));
                } else {
                    graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, firstGraphics)));
                }
            }   else {
                graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, firstGraphics)));
            }
        }

        if (!graphics[1].contains(" ")) {
            graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, graphics[1])));
        } else {
            String secondGraphics = graphics[1];
            if (secondGraphics.startsWith(" ")) {
                secondGraphics = secondGraphics.replaceFirst(" ", "");
            }
            if (checkIsGraphicsBrandValid(secondGraphics)) {
                if (secondGraphics.contains(" ")) {
                    String secondGraphicsBrand = secondGraphics.substring(0, secondGraphics.indexOf(" "));
                    String secondGraphicsVersion = secondGraphics.substring(secondGraphics.indexOf(" ") + 1);
                    graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(secondGraphicsBrand, secondGraphicsVersion, null)));
                } else {
                    graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, secondGraphics)));
                }
            } else {
                graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, secondGraphics)));
            }
        }
        return graphicsList;
    }

    private Boolean checkIsGraphicsBrandValid(String graphics) {
        try {
            graphics = graphics.substring(0, graphics.indexOf(" "));
            List<String> validBrands = Arrays.asList("NVIDIA", "AMD", "OPENGL", "Intel", "NVidia", "Radeon", "Radeon(R)", "Nvidia", "NVIDIA®", "NVIDIA(R)", "AMD(R)", "Intel(R)", "NVIDA");
            return validBrands.contains(graphics);
        } catch (Exception e) {
            log.error("Graphics: " + graphics + " is not valid");
            log.error("Exception: " + e.getMessage());
        }
        return false;
    }

    public Set<Graphics> extractGraphicsForEpicGames(String graphics) {
        Set<Graphics> graphicsList = new HashSet<>();
        if (graphics.contains("/")) {
            String[] split = graphics.split("/", 2);
            graphicsList = new HashSet<>(extractGraphics(split).stream().toList());
            return graphicsList;
        } else if (graphics.contains(" or ")) {
            String[] split = graphics.split(" or ", 2);
            graphicsList = new HashSet<>(extractGraphics(split));
            return graphicsList;
        } else if (graphics.contains(" ")) {
            String[] split = graphics.split(" ", 2);
            String brand = split[0];
            String version = split[1];
            graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(brand, version, null)));
            return graphicsList;
        } else {
            graphicsList.add(this.ifGraphicsExistsGetGraphicsOrCreate(new CreateGraphicsRequest(null, null, graphics)));
            return graphicsList;
        }
    }

    

    protected Graphics ifGraphicsExistsGetGraphicsOrCreate(CreateGraphicsRequest request) {
        return graphicsRepository
                .findByBrandAndVersionAndDescription(request.brand(), request.version(), request.description())
                .orElseGet(() -> graphicsRepository
                        .save(Graphics
                                .builder()
                                .brand(request.brand())
                                .version(request.version())
                                .description(request.description())
                                .build()));
    }
}
