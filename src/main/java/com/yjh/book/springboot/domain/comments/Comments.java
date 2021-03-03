package com.yjh.book.springboot.domain.comments;

import com.yjh.book.springboot.domain.posts.BaseTimeEntity;
import com.yjh.book.springboot.domain.posts.Posts;
import com.yjh.book.springboot.domain.user.User;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "posts")
@NoArgsConstructor
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENTS_NO")
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "CREATED_BY", nullable = false)
    private String created_by;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comments(String content, String created_by, Posts post, User user) {
        this.content = content;
        this.created_by = created_by;
        this.post = post;
        this.user = user;
    }

    public void changeAuthor(User author) {
        this.user = author;
    }
    public void changePost(Posts post) {
        this.post = post;
    }
    public void update(String content) {
        this.content = content;
    }
}
