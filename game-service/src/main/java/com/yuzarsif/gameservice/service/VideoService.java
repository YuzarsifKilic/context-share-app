package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.model.Video;
import com.yuzarsif.gameservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }
}
