package com.yuzarsif.movieservice.service;

import com.yuzarsif.movieservice.dto.CreateStarRequest;
import com.yuzarsif.movieservice.exception.EntityNotFoundException;
import com.yuzarsif.movieservice.model.Star;
import com.yuzarsif.movieservice.repository.StarRepository;
import com.yuzarsif.movieservice.utils.DateConverter;
import org.springframework.stereotype.Service;

@Service
public class StarService {

    private final StarRepository starRepository;

    public StarService(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    protected Star findById(Long starId) {
        return starRepository.findById(starId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Star with id %s not found", starId)));
    }

    public void save(CreateStarRequest request) {
        Star star = Star
                .builder()
                .name(request.name())
                .bio(request.bio())
                .height(request.height())
                .imageUrl(request.imageUrl())
                .birthDate(DateConverter.convert(request.birthDate()))
                .bornPlace(request.bornPlace())
                .build();

        starRepository.save(star);
    }

    public void delete(Long starId){
        findById(starId);
        starRepository.deleteById(starId);
    }
}
