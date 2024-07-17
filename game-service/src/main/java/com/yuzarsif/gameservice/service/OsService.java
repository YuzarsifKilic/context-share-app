package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.OsDto;
import com.yuzarsif.gameservice.dto.request.CreateOsRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Os;
import com.yuzarsif.gameservice.repository.OsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OsService {

    private final OsRepository osRepository;

    public OsService(OsRepository osRepository) {
        this.osRepository = osRepository;
    }

    public Os findById(Long id) {
        return osRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Os not found with id " + id));
    }

    public List<Os> findByIdList(List<Long> idList) {
        return idList
                .stream()
                .map(this::findById)
                .toList();
    }

    public List<OsDto> findByNameContaining(String name) {
        return osRepository
                .findByBrandContainingOrVersionContaining(name, name)
                .stream()
                .map(OsDto::convert)
                .toList();
    }

    public void create(CreateOsRequest request) {
        Os os = Os
                .builder()
                .brand(request.brand())
                .version(request.version())
                .build();

        osRepository.save(os);
    }
}
