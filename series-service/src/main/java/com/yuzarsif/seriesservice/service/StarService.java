package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.StarDto;
import com.yuzarsif.seriesservice.dto.request.CreateStarRequest;
import com.yuzarsif.seriesservice.model.Star;
import com.yuzarsif.seriesservice.repository.StarRepository;
import com.yuzarsif.seriesservice.utils.DateConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarService {

    private final StarRepository starRepository;

    public StarService(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    protected Star findById(Long id) {
        return starRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Star not found with id: " + id));
    }

    public StarDto getStarById(Long id) {
        return StarDto.convert(findById(id));
    }

    public List<StarDto> findStarsByName(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return starRepository.findByNameContaining(name, pageable)
                .stream()
                .map(StarDto::convert)
                .toList();
    }

    public StarDto createStar(CreateStarRequest request) {
        Star star = Star.builder()
                .name(request.name())
                .bio(request.bio())
                .birthDate(DateConverter.convert(request.birthDate()))
                .height(request.height())
                .bornPlace(request.bornPlace())
                .imageUrl(request.imageUrl())
                .build();

        Star savedStar = starRepository.save(star);

        return StarDto.convert(savedStar);
    }
}
