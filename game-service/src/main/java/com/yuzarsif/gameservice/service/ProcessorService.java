package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.ProcessorDto;
import com.yuzarsif.gameservice.dto.request.CreateProcessorRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Processor;
import com.yuzarsif.gameservice.repository.ProcessorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProcessorService {

    private final ProcessorRepository processorRepository;

    public ProcessorService(ProcessorRepository processorRepository) {
        this.processorRepository = processorRepository;
    }

    public Processor findById(Long id) {
        return processorRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Processor not found with id " + id));
    }

    public Set<Processor> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .collect(Collectors.toSet());
    }

    public List<ProcessorDto> findByName(String search) {
        return processorRepository
                .findByBrandContainingOrVersionContaining(search, search)
                .stream()
                .map(ProcessorDto::convert)
                .toList();
    }

    public void create(CreateProcessorRequest request) {
        Processor processor = Processor
                .builder()
                .brand(request.brand())
                .version(request.version())
                .build();

        processorRepository.save(processor);
    }

    protected List<Processor> getProcessor(String processor) {
        log.error("Processor: " + processor);
        List<Processor> processorList = new ArrayList<>();

        String[] processors = new String[2];

        processor = processor.substring(11);
        if (processor.isEmpty()) {
            return processorList;
        }
        if (processor.contains("/")) {
            processors  = processor.split("/", 2);
            processorList = this.extractProcessors(processors);
            return processorList;
        } else if (processor.contains(" or ")) {
           processors  = processor.split(" or ", 2);
            processorList = this.extractProcessors(processors);
            return processorList;
        } else if (processor.contains(",")) {
            if (!processor.contains(" ")) {
                log.info("Processor: " + processor);
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, processor)));
                return processorList;
            }
            String[] checkedLength  = processor.split(",", 2);
            if (checkedLength[1].length() < 12) {
                String brand = processor.substring(0, processor.indexOf(" "));
                String version = processor.substring(processor.indexOf(" ")+1);
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(brand, version, null)));
                return processorList;
            }
            if (processor.contains(", ")) {
                processors  = processor.split(", ", 2);
                processorList = this.extractProcessors(processors);
                return processorList;
            }
            return processorList;
        } else {
            if (!processor.contains(" ")) {
                log.info("Processor: " + processor);
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, processor)));
                return processorList;
            }
            log.info("Processor: " + processor);
            if (this.checkProcessorBrandValid(processor)) {
                String brand = processor.substring(0, processor.indexOf(" "));
                String version = processor.substring(processor.indexOf(" ")+1);
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(brand, version, null)));
            } else {
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, processor)));
            }
            return processorList;
        }
    }

    private Boolean checkProcessorBrandValid(String processor) {
        List<String> brands = Arrays.asList("Intel", "AMD", "Intel(R)", "AMD(R)", "Radeon", "Radeon(R)", "NVIDIA", "NVIDIA(R)", "Intel®", "INTEL®");
        return brands.contains(processor);
    }

    private List<Processor> extractProcessors(String[] processors) {
        List<Processor> processorList = new ArrayList<>();
        String firstProcessor = processors[0];
        if (firstProcessor.startsWith(" ")) {
            firstProcessor = firstProcessor.replaceFirst(" ", "");
        }
        if (!firstProcessor.contains(" ")) {
            processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, firstProcessor)));
        } else {
            if (this.checkProcessorBrandValid(firstProcessor)) {
                if (firstProcessor.contains(" ")) {
                    String firstProcessorBrand = firstProcessor.substring(0, firstProcessor.indexOf(" "));
                    String firstProcessorVersion = firstProcessor.substring(firstProcessor.indexOf(" ")+1);
                    processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(firstProcessorBrand, firstProcessorVersion, null)));
                } else {
                    processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, firstProcessor)));
                }
            } else {
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, firstProcessor)));
            }
        }
        if (!processors[1].contains(" ")) {
            processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, processors[1])));
        } else {
            String secondProcessor = processors[1];
            if (secondProcessor.startsWith(" ")) {
                secondProcessor = secondProcessor.replaceFirst(" ", "");
            }
            if (this.checkProcessorBrandValid(secondProcessor)) {
                if (secondProcessor.contains(" ")) {
                    String secondProcessorBrand = secondProcessor.substring(0, secondProcessor.indexOf(" "));
                    String secondProcessorVersion = secondProcessor.substring(secondProcessor.indexOf(" ")+1);
                    processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(secondProcessorBrand, secondProcessorVersion, null)));
                } else {
                    processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, secondProcessor)));
                }
            } else {
                processorList.add(this.ifProcessorExistsGetProcessorOrCreate(new CreateProcessorRequest(null, null, secondProcessor)));
            }
        }
        return processorList;
    }

    private Processor ifProcessorExistsGetProcessorOrCreate(CreateProcessorRequest request) {
        if (request.brand() == null && request.version() == null && request.description() == null) {
            return null;
        }
        Optional<Processor> optionalProcessor = processorRepository.findByBrandAndVersionAndDescription(request.brand(), request.version(), request.description());
        if (request.description() == null) {
            return optionalProcessor.orElseGet(() -> processorRepository.save(Processor
                    .builder()
                    .brand(request.brand())
                    .version(request.version())
                    .build()));
        } else {
            return optionalProcessor.orElseGet(() -> processorRepository.save(Processor
                    .builder()
                    .brand(request.brand())
                    .version(request.version())
                    .description(request.description())
                    .build()));
        }
    }
}
