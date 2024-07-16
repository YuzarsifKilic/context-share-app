package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.ProcessorDto;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Processor;
import com.yuzarsif.gameservice.repository.ProcessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Processor> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .toList();
    }

    public List<ProcessorDto> findByName(String search) {
        return processorRepository
                .findByBrandContainingOrVersionContaining(search, search)
                .stream()
                .map(ProcessorDto::convert)
                .toList();
    }
}
