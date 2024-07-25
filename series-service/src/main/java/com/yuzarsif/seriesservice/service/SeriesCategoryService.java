package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.repository.SeriesCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SeriesCategoryService {

    private final SeriesCategoryRepository seriesCategoryRepository;

    public SeriesCategoryService(SeriesCategoryRepository seriesCategoryRepository) {
        this.seriesCategoryRepository = seriesCategoryRepository;
    }
}
