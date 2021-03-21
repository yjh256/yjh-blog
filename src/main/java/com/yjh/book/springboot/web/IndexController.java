package com.yjh.book.springboot.web;

import com.yjh.book.springboot.config.auth.LoginUser;
import com.yjh.book.springboot.config.auth.dto.SessionUser;
import com.yjh.book.springboot.domain.user.User;
import com.yjh.book.springboot.domain.user.UserRepository;
import com.yjh.book.springboot.service.comments.CommentsService;
import com.yjh.book.springboot.service.posts.PostsService;
import com.yjh.book.springboot.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserRepository userRepository;
    private final PostsService postsService;
    private final CommentsService commentsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable) { // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        model.addAttribute("classification", "");
        model.addAttribute("posts", postsService.findAll(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence("", pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/board/{classification}")
    public String classification(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable, @PathVariable String classification) { // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        model.addAttribute("classification", classification);
        model.addAttribute("posts", postsService.findByClassification(pageable, classification));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence("", pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/search")
    public String search(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable, @RequestParam(value = "keyword") String keyword) {
        model.addAttribute("classification", "");
        model.addAttribute("posts", postsService.findByTitle(pageable, keyword));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence("", pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/search/{classification}")
    public String search(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable, @PathVariable String classification, @RequestParam(value = "keyword") String keyword) {
        model.addAttribute("classification", classification);
        model.addAttribute("posts", postsService.findByTitleInClassification(pageable, keyword, classification));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence("", pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String post(Model model, @LoginUser SessionUser user, @PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);
        postsService.updateView(id);
        model.addAttribute("post",dto);
        model.addAttribute("comments", commentsService.listsComments(id));
        if (user != null) {
            User user2 = userRepository.findByEmail(user.getEmail()).get();
            model.addAttribute("userId", user2.getId());
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
