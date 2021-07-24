package com.yjh.blog.web.dto.comments;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentsUpdateRequestDto {
    private Long post_id;
    private Long user_id;
    private String content;
    private String created_by;

    @Builder
    public CommentsUpdateRequestDto(Long post_id, Long user_id, String content, String created_by) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
        this.created_by = created_by;
    }
}
