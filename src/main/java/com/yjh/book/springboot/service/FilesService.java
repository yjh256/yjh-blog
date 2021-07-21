package com.yjh.book.springboot.service;

import com.yjh.book.springboot.domain.files.Files;
import com.yjh.book.springboot.domain.files.FilesRepository;
import com.yjh.book.springboot.web.dto.Files.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FilesService {
    private final FilesRepository filesRepository;

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return filesRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id) {
        Files file = filesRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFileName(file.getOrigFileName())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}
