package com.yjh.book.springboot.web.dto.comments;

import com.yjh.book.springboot.domain.comments.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsListResponseDto {
    private String created_by;
    private String content;
    private LocalDateTime modifiedDate;

    public CommentsListResponseDto(Comments entity) {
        this.created_by = entity.getCreated_by();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
    }
}
