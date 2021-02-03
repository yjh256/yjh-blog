package com.yjh.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String classification;

    @Builder
    public Posts(String title, String content, String classification){
        this.title = title;
        this.content = content;
        this.classification = classification;
    }

    public void update(String title, String content, String classification) {
        this.title = title;
        this.content = content;
        this.classification = classification;
    }
}
