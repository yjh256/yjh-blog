package com.yjh.book.springboot.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsUpdateRequestDto {
    private String title;
    private String content;
    private String classification;
    private Long fileId;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String classification, Long fileId){
        this.title = title;
        this.content = content;
        this.classification = classification;
        this.fileId = fileId;
    }
}
