package com.yjh.book.springboot.web;

import com.yjh.book.springboot.config.auth.LoginUser;
import com.yjh.book.springboot.config.auth.dto.SessionUser;
import com.yjh.book.springboot.service.posts.PostsService;
import com.yjh.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.Session;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/{classification}")
    public String classification(Model model, @LoginUser SessionUser user, @PathVariable String classification) {
        model.addAttribute("posts", postsService.findByClassification(classification));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "classification";
    }

    @GetMapping("/posts/{id}")
    public String post(Model model, @LoginUser SessionUser user, @PathVariable Long id) {
        model.addAttribute("post", postsService.findById(id));
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
