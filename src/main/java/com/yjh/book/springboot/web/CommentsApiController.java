package com.yjh.book.springboot.web;

import com.yjh.book.springboot.service.CommentsService;
import com.yjh.book.springboot.web.dto.comments.CommentsListResponseDto;
import com.yjh.book.springboot.web.dto.comments.CommentsSaveRequestDto;
import com.yjh.book.springboot.web.dto.comments.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentsService commentsService;

    @PostMapping("/api/v1/comments")
    public Long createComment(@RequestBody CommentsSaveRequestDto requestDto) {
        return commentsService.createComment(requestDto);
    }

    @PutMapping("/api/v1/comments/{commentNo}")
    public Long update(@PathVariable Long commentNo, @RequestBody CommentsUpdateRequestDto requestDto) {
        return commentsService.modifyComments(commentNo, requestDto);
    }

    @GetMapping("/api/v1/comments/{id}")
    public List<CommentsListResponseDto> ListComments(@PathVariable Long id) {
        return commentsService.listsComments(id);
    }

    @DeleteMapping("/api/v1/comments/{commentNo}")
    public Long delete(@PathVariable Long commentNo) {
        commentsService.deleteComments(commentNo);
        return commentNo;
    }
}
