package com.yjh.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블에 링크될 클래스임을 나타낸다. * Entity 클래스에서는 절대 setter 메소드를 만들지 않는다.
@DynamicInsert
@DynamicUpdate
public class Posts extends BaseTimeEntity{

    @Id // 테이블의 PK field를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타낸다. GenerationType.IDENTITY 옵션을 추가해야 auto_increment가 된다.
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼을 나타내며 굳이 선언할 필요는 없다. 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String classification;

    @Column(columnDefinition = "integer default 0")
    private Integer view;

    @Column
    private Long fileId;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 field만 빌더에 포함
    public Posts(String title, String content, String classification, Long fileId){
        this.title = title;
        this.content = content;
        this.classification = classification;
        this.fileId = fileId;
    }

    public void update(String title, String content, String classification, Long fileId) {
        this.title = title;
        this.content = content;
        this.classification = classification;
        this.fileId = fileId;
    }
}
