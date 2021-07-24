package com.yjh.blog.web.dto.posts;

import com.yjh.blog.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String classification;
    private LocalDateTime modifiedDate;
    private int view;
    private Long fileId;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.classification = entity.getClassification();
        this.modifiedDate = entity.getModifiedDate();
        this.view = entity.getView();
        this.fileId = entity.getFileId();
    }
}
