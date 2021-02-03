package com.yjh.book.springboot.web.dto;

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
    @Builder
    public PostsSaveRequestDto(String title, String content, String classfication){
        this.title = title;
        this.content = content;
        this.classification = classfication;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .classification(classification)
                .build();
    }
}
