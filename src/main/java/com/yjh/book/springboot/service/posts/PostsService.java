package com.yjh.book.springboot.service.posts;

import com.yjh.book.springboot.domain.posts.Posts;
import com.yjh.book.springboot.domain.posts.PostsRepository;
import com.yjh.book.springboot.web.dto.PostsListResponseDto;
import com.yjh.book.springboot.web.dto.PostsResponseDto;
import com.yjh.book.springboot.web.dto.PostsSaveRequestDto;
import com.yjh.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));

        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getClassification());
        return id;

    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<Posts> getPostsList(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    @Transactional
    public Page<Posts> findByClassification(Pageable pageable, String classification) {
        return postsRepository.findByClassification(pageable, classification);
    }

    @Transactional
    public int getPageStart(Pageable pageable) {
        Page<Posts> posts = getPostsList(pageable);
        return (int)(posts.getNumber() / 10) * 10 + 1;
    }

    @Transactional
    public int getPageLast(Pageable pageable, int start) {
        Page<Posts> posts = getPostsList(pageable);
        return start + 9 < posts.getTotalPages() ? start + 9 : posts.getTotalPages();
    }

    @Transactional
    public int[] getPageSequence(int start, int last) {
        int[] list = new int[last-start+1];
        int j = 0;
        for (int i = start; i <= last; i++) {
            list[j] = i;
            j++;
        }
        return list;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}
