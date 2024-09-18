package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.model.Memory;
import com.yuzarsif.gameservice.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemoryService {

    private final MemoryRepository repository;

    public Memory clearData(String memory) {
        if (memory.matches("\\d+")) {
            Memory memory1 = Memory
                    .builder()
                    .size(Integer.parseInt(memory))
                    .build();

            return repository.save(memory1);
        }
        String[] result = memory.split("(?<=\\d)(?=\\D)", 2);
        return ifMemoryExistsGetMemoryOrCreate(result);
    }

    public Memory ifMemoryExistsGetMemoryOrCreate(String[] memory) {
        try {
            Integer size = Integer.parseInt(memory[0]);
            String description = memory[1];
            return repository
                    .findBySizeAndDescription(size, description)
                    .orElseGet(() -> {
                        Memory memory1 = new Memory();
                        memory1.setSize(size);
                        memory1.setDescription(description);
                        return repository.save(memory1);
                    });
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return null;
        }

    }
}
