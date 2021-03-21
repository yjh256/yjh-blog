package com.yjh.book.springboot.service.posts;

import com.yjh.book.springboot.domain.posts.Posts;
import com.yjh.book.springboot.domain.posts.PostsRepository;
import com.yjh.book.springboot.web.dto.posts.PostsListResponseDto;
import com.yjh.book.springboot.web.dto.posts.PostsResponseDto;
import com.yjh.book.springboot.web.dto.posts.PostsSaveRequestDto;
import com.yjh.book.springboot.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts); // Entity를 파라미터로 삭제할 수도 있고, deleteById 메소드를 이용해 id로 삭제할 수도 있다.
    }

    @Transactional
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // readOnly = true는 트랙잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도를 개선한다. 그래서 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용한다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // posts -> new PostsListResponseDto(posts)와 같다.
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<Posts> findAll(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return postsRepository.findAll(pageable);
    }

    @Transactional
    public Page<Posts> findByClassification(Pageable pageable, String classification) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return postsRepository.findByClassification(pageable, classification);
    }

    @Transactional
    public Page<Posts> findByTitle(Pageable pageable, String keyword) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return postsRepository.findByTitleContaining(pageable, keyword);
    }

    @Transactional
    public Page<Posts> findByTitleInClassification(Pageable pageable, String keyword, String classification) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return postsRepository.findByTitleContainingAndClassification(pageable, keyword, classification);
    }

    @Transactional
    public int[] getPageSequence(String classification, Pageable pageable) {
        Page<Posts> posts;
        if (classification == "") posts = findAll(pageable);
        else posts = findByClassification(pageable, classification);
        int start = (int)(posts.getNumber() / 10) * 10 + 1;
        int last = start + 9 < posts.getTotalPages() ? start + 9 : posts.getTotalPages();
        int[] list = new int[last-start+1];
        int j = 0;
        for (int i = start; i <= last; i++) {
            list[j] = i;
            j++;
        }
        return list;
    }

    @Transactional
    public  int updateView(Long id) {
        return postsRepository.updateView(id);
    }
}
