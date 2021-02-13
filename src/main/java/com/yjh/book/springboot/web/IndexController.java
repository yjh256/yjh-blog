package com.yjh.book.springboot.web;

import com.yjh.book.springboot.config.auth.LoginUser;
import com.yjh.book.springboot.config.auth.dto.SessionUser;
import com.yjh.book.springboot.domain.posts.Posts;
import com.yjh.book.springboot.service.posts.PostsService;
import com.yjh.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @PageableDefault(size=10, sort="id", direction= Sort.Direction.DESC)
            Pageable pageable) {
        int start = postsService.getPageStart(pageable);
        int last = postsService.getPageLast(pageable, start);
        model.addAttribute("posts", postsService.getPostsList(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence(start, last));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/{classification}")
    public String classification(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable, @PathVariable String classification) {
        int start = postsService.getPageStart(pageable);
        int last = postsService.getPageLast(pageable, start);
        model.addAttribute("posts", postsService.findByClassification(pageable, classification));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber()+1);
        model.addAttribute("numbers", postsService.getPageSequence(start, last));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String post(Model model, @LoginUser SessionUser user, @PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "post";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "posts-update";
    }
}
