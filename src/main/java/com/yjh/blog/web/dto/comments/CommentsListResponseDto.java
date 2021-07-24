package com.yjh.blog.web.dto.comments;

import com.yjh.blog.domain.comments.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsListResponseDto {
    private Long id;
    private String created_by;
    private String content;
    private LocalDateTime modifiedDate;

    public CommentsListResponseDto(Comments entity) {
        this.id = entity.getId();
        this.created_by = entity.getCreated_by();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
    }
}
