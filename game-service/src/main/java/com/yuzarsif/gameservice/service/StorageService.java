package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.model.Storage;
import com.yuzarsif.gameservice.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

    private final StorageRepository repository;

    public Storage clearData(String storage) {
        if (storage.matches("\\d+")) {
            Storage storage1 = Storage
                    .builder()
                    .size(Integer.parseInt(storage))
                    .build();
            return repository.save(storage1);
        }
        String[] result = storage.split("(?<=\\d)(?=\\D)", 2);
        return ifStorageExistsGetStorageOrCreate(result);
    }

    public Storage ifStorageExistsGetStorageOrCreate(String[] storage) {
        try {
            Integer size = Integer.parseInt(storage[0]);
            String description = storage[1];
            return repository
                    .findBySizeAndDescription(size, description)
                    .orElseGet(() -> {
                        Storage storage1 = new Storage();
                        storage1.setSize(size);
                        storage1.setDescription(description);
                        return repository.save(storage1);
                    });
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return null;
        }

    }
}
