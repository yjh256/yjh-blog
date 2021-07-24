package com.yjh.blog.domain.comments;

import com.yjh.blog.domain.posts.Posts;
import com.yjh.blog.domain.posts.PostsRepository;
import com.yjh.blog.domain.user.Role;
import com.yjh.blog.domain.user.User;
import com.yjh.blog.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentsRepositoryTest {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        commentsRepository.deleteAll();
        postsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String commentsContent = "테스트 댓글 본문";
        String name = "사용자 이름";
        String email = "사용자 이메일";
        String picture = "사용자 사진";
        Role role = Role.USER;

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .classification("html")
                .build();
        postsRepository.save(posts);

        User user = User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build();
        userRepository.save(user);

        commentsRepository.save(Comments.builder()
                .content(commentsContent)
                .created_by("생성일자")
                .post(posts)
                .user(user)
                .build());

        //when
        List<Comments> commentsList = commentsRepository.getCommentsOfPost(posts.getId());

        //then
        Comments comments = commentsList.get(0);
        assertThat(comments.getContent()).isEqualTo(commentsContent);
    }
}


