package com.yjh.book.springboot.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsUpdateRequestDto {
    private String title;
    private String content;
    private String classification;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String classification){
        this.title = title;
        this.content = content;
        this.classification = classification;
    }
}
