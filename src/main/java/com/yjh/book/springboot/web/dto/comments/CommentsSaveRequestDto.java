package com.yjh.book.springboot.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsSaveRequestDto {
    private Long post_id;
    private Long user_id;
    private String content;
    private String created_by;

    @Builder
    public CommentsSaveRequestDto(Long post_id, Long user_id, String content, String created_by) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
        this.created_by = created_by;
    }

}
