package com.yuzarsif.seriesservice.service;

import com.yuzarsif.seriesservice.dto.WriterDto;
import com.yuzarsif.seriesservice.model.Writer;
import com.yuzarsif.seriesservice.repository.WriterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriterService {

    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    protected Writer findById(Long id) {
        return writerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Writer not found with id: " + id));
    }

    public WriterDto getWriterById(Long id) {
        return WriterDto.convert(findById(id));
    }

    public List<WriterDto> findWritersByName(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return writerRepository.findByNameContaining(name, pageable)
                .stream()
                .map(WriterDto::convert)
                .toList();
    }
}
