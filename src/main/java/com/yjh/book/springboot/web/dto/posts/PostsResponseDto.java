package com.yjh.book.springboot.web.dto.posts;

import com.yjh.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String classification;
    private int view;
    private Long fileId;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.classification = entity.getClassification();
        this.view = entity.getView();
        this.fileId = entity.getFileId();
    }
}
