package com.yjh.book.springboot.web.dto.Files;

import com.yjh.book.springboot.domain.files.Files;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String origFileName;
    private String fileName;
    private String filePath;

    public Files toEntity() {
        Files file = Files.builder()
                .id(id)
                .origFileName(origFileName)
                .fileName(fileName)
                .filePath(filePath)
                .build();
        return file;
    }

    @Builder
    public FileDto(Long id, String origFileName, String fileName, String filePath) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
