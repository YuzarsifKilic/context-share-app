package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.model.Photo;
import com.yuzarsif.gameservice.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Photo createPhoto(Photo photo) {
        return photoRepository.save(photo);
    }
}
