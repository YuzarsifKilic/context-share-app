package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.OsDto;
import com.yuzarsif.gameservice.dto.request.CreateOsRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Os;
import com.yuzarsif.gameservice.repository.jpa.OsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    protected Os getOs(String os) {
        log.info("OS: " + os);
        if (os.contains("OS *: ")) {
            os = os.substring(6);
            log.info("After substring: " + os);
            if (os.contains("/")) {
                String[] clearedOs = os.split("/", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            } if (os.contains(",")) {
                String[] clearedOs = os.split(",", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            } else {
                if (os.contains("  ")) {
                    String[] clearedOs = os.split(" {2}", 2);
                    String brand = clearedOs[0];
                    String version = clearedOs[1];
                    return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
                }
                String[] clearedOs = os.split(" ", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            }
        } else if (os.contains("OS *:")) {
            os = os.substring(5);
            if (os.contains("/")) {
                String[] clearedOs = os.split("/", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            } if (os.contains(",")) {
                String[] clearedOs = os.split(",", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            } else {
                String[] clearedOs = os.split(" ", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            }
        } else if (os.contains("OS: ")) {
            os = os.substring(4);
            if (os.contains("/")) {
                String[] clearedOs = os.split("/", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            } if (os.contains(",")) {
                String[] clearedOs = os.split(",", 2);
                String brand = clearedOs[0];
                String version = clearedOs[1];
                return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
            } else {
                if (!os.contains(" ")) {
                    return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(null, null, os));
                } else {
                    String[] clearedOs = os.split(" ", 2);
                    String brand = clearedOs[0];
                    String version = clearedOs[1];
                    return this.ifOsExistsGetOsOrCreate(new CreateOsRequest(brand, version, null));
                }
            }
        }
        log.error("OS is not good for us: " + os);
        return null;
    }

    private Os ifOsExistsGetOsOrCreate(CreateOsRequest request) {
        Optional<Os> optionalOs = osRepository.findByBrandAndVersionAndDescription(request.brand(), request.version(), request.description());
        return optionalOs.orElseGet(() -> osRepository.save(Os
                .builder()
                .brand(request.brand())
                .version(request.version())
                .description(request.description())
                .build()));
    }
}