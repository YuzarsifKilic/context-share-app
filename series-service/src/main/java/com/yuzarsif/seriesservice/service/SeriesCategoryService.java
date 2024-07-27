package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.SeriesCategoryDto;
import com.yuzarsif.seriesservice.model.SeriesCategory;
import com.yuzarsif.seriesservice.repository.SeriesCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesCategoryService {

    private final SeriesCategoryRepository seriesCategoryRepository;

    public SeriesCategoryService(SeriesCategoryRepository seriesCategoryRepository) {
        this.seriesCategoryRepository = seriesCategoryRepository;
    }

    protected SeriesCategory findById(Long id) {
        return seriesCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeriesCategory not found with id: " + id));
    }

    public List<SeriesCategoryDto> getSeriesCategories() {
        return seriesCategoryRepository.findAll()
                .stream()
                .map(SeriesCategoryDto::convert)
                .toList();
    }
}
