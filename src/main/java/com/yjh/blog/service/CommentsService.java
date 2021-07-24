package com.yjh.blog.service;

import com.yjh.blog.domain.comments.Comments;
import com.yjh.blog.domain.comments.CommentsRepository;
import com.yjh.blog.domain.posts.Posts;
import com.yjh.blog.domain.posts.PostsRepository;
import com.yjh.blog.domain.user.User;
import com.yjh.blog.domain.user.UserRepository;
import com.yjh.blog.web.dto.comments.CommentsListResponseDto;
import com.yjh.blog.web.dto.comments.CommentsSaveRequestDto;
import com.yjh.blog.web.dto.comments.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;

    // 댓글 등록
    @Transactional
    public Long createComment(CommentsSaveRequestDto requestDto) {
        Posts post = postsRepository.findById((Long)requestDto.getPost_id()).get();
        User user = userRepository.findById((Long)requestDto.getUser_id()).get();
        Comments comment = Comments.builder()
                .content(requestDto.getContent())
                .created_by(requestDto.getCreated_by())
                .post(post)
                .user(user)
                .build();
        return commentsRepository.save(comment).getId();
    }

    // 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentsListResponseDto> listsComments(long postNo) {
        return commentsRepository.getCommentsOfPost(postNo).stream()
                .map(CommentsListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComments(long commentsNo) {
        Comments comments = commentsRepository.findById(commentsNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentNo = "+ commentsNo));
        commentsRepository.delete(comments);
    }

    // 댓글 수정
    @Transactional
    public Long modifyComments(Long commentNo, CommentsUpdateRequestDto requestDto) {
        Comments modifyComment = commentsRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentNo = "+commentNo));;
        modifyComment.update(requestDto.getContent());
        return commentNo;
    }
}
