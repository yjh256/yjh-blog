package com.yjh.book.springboot.web.dto.posts;

import com.yjh.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String classification;
    private Long fileId;

    @Builder
    public PostsSaveRequestDto(String title, String content, String classfication, Long fileId){
        this.title = title;
        this.content = content;
        this.classification = classfication;
        this.fileId = fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .classification(classification)
                .fileId(fileId)
                .build();
    }
}
