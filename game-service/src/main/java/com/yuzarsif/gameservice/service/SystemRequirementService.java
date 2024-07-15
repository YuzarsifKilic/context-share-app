package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.SystemRequirement;
import com.yuzarsif.gameservice.repository.SystemRequirementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemRequirementService {

    private final SystemRequirementRepository systemRequirementRepository;

    public SystemRequirementService(SystemRequirementRepository systemRequirementRepository) {
        this.systemRequirementRepository = systemRequirementRepository;
    }

    public SystemRequirement findById(Long id) {
        return systemRequirementRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SystemRequirement not found with id " + id));
    }
}
