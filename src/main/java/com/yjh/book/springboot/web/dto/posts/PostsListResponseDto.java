package com.yjh.book.springboot.web.dto.posts;

import com.yjh.book.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String classification;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.classification = entity.getClassification();
        this.modifiedDate = entity.getModifiedDate();
    }
}
