package com.yuzarsif.movieservice.service;

import com.yuzarsif.movieservice.dto.CreateWriterRequest;
import com.yuzarsif.movieservice.exception.EntityNotFoundException;
import com.yuzarsif.movieservice.model.Writer;
import com.yuzarsif.movieservice.repository.WriterRepository;
import com.yuzarsif.movieservice.utils.DateConverter;
import org.springframework.stereotype.Service;

@Service
public class WriterService {

    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    protected Writer findById(Long writerId) {
        return writerRepository.findById(writerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Writer with id %s not found", writerId)));
    }

    public void save(CreateWriterRequest request) {
        Writer writer = Writer
                .builder()
                .name(request.name())
                .bio(request.bio())
                .birthDate(DateConverter.convert(request.birthDate()))
                .bornPlace(request.bornPlace())
                .height(request.height())
                .url(request.imageUrl())
                .build();

        writerRepository.save(writer);
    }

    public void delete(Long writerId){
        findById(writerId);
        writerRepository.deleteById(writerId);
    }
}
