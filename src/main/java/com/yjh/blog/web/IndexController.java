package com.yjh.blog.web;

import com.yjh.blog.domain.user.User;
import com.yjh.blog.domain.user.UserRepository;
import com.yjh.blog.service.PostsService;
import com.yjh.blog.web.dto.posts.PostsSaveRequestDto;
import com.yjh.blog.web.dto.posts.PostsUpdateRequestDto;
import com.yjh.blog.config.auth.LoginUser;
import com.yjh.blog.config.auth.dto.SessionUser;
import com.yjh.blog.domain.files.MD5Generator;
import com.yjh.blog.service.CommentsService;
import com.yjh.blog.service.FilesService;
import com.yjh.blog.web.dto.Files.FileDto;
import com.yjh.blog.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserRepository userRepository;
    private final PostsService postsService;
    private final CommentsService commentsService;
    private final FilesService filesService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable) { // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        model.addAttribute("classification", false);
        model.addAttribute("posts", postsService.findAll(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence("", pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
            String role = user.getRole();
            if (role == "ROLE_USER") {
                model.addAttribute("RoleUSER", true);
            } else {
                model.addAttribute("RoleUSER", false);
            }
        }
        return "index";
    }

    @GetMapping("/logging-in")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/board/{classification}")
    public String classification(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable, @PathVariable String classification) { // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        model.addAttribute("classification", classification);
        model.addAttribute("posts", postsService.findByClassification(pageable, classification));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence(classification, pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
            String role = user.getRole();
            if (role.equals("ROLE_USER")) {
                model.addAttribute("RoleUSER", true);
            } else {
                model.addAttribute("RoleUSER", false);
            }
        }

        return "board";
    }

    @GetMapping("/search/{classification}")
    public String search(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable, @PathVariable String classification, @RequestParam(value = "keyword") String keyword) {
        model.addAttribute("classification", classification);
        model.addAttribute("posts", postsService.findByTitleInClassification(pageable, keyword, classification));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("numbers", postsService.getPageSequence(classification, pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "board";
    }

    @GetMapping("/posts/{id}")
    public String post(Model model, @LoginUser SessionUser user, @PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);
        if (dto.getFileId() != null) {
            String filename = filesService.getFile(dto.getFileId()).getOrigFileName();
            model.addAttribute("filename", filename);
        }
        postsService.updateView(id);
        model.addAttribute("post",dto);
        model.addAttribute("comments", commentsService.listsComments(id));
        if (user != null) {
            User user2 = userRepository.findByEmail(user.getEmail()).get();
            model.addAttribute("userId", user2.getId());
            model.addAttribute("userName", user.getName());
            String role = user.getRole();
            if (role.equals("ROLE_USER")) {
                model.addAttribute("RoleUSER", true);
            } else {
                model.addAttribute("RoleUSER", false);
            }
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

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        FileDto fileDto = filesService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        String fileName = URLEncoder.encode(fileDto.getOrigFileName()).replaceAll("\\+", "%20");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PostMapping("/api/v1/posts")
    public String save(@RequestParam("file") MultipartFile files,
                       @RequestParam("title") String title,
                       @RequestParam("classification") String classification,
                       @RequestParam("content") String content) {
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .classfication(classification)
                .content(content)
                .build();
        Long fileId = saveFile(files);
        if (fileId != -1L) {
            requestDto.setFileId(fileId);
        }
        postsService.save(requestDto);

        return "redirect:/board/"+classification;
    }

    @PostMapping("/api/v1/posts/{id}")
    public String update(@PathVariable Long id,
                       @RequestParam("file") MultipartFile files,
                       @RequestParam("title") String title,
                       @RequestParam("classification") String classification,
                       @RequestParam("content") String content){
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(title)
                .classification(classification)
                .content(content)
                .build();
        Long fileId = saveFile(files);
        if (fileId != -1L) {
            requestDto.setFileId(fileId);
        }
        postsService.update(id, requestDto);
        return "redirect:/board/"+classification;
    }

    private Long saveFile(MultipartFile files) {
        try {
            String origFileName = files.getOriginalFilename();
            MultipartFile file = files;
            if (origFileName.length() == 0) {
                return -1L;
            }
            String fileName = new MD5Generator(origFileName).toString();
            String savePath = System.getProperty("user.dir") + "\\files";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch(Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + fileName;
            file.transferTo(new File(filePath));

            FileDto fileDto = FileDto.builder()
                    .origFileName(origFileName)
                    .fileName(fileName)
                    .filePath(filePath)
                    .build();

            Long fileId = filesService.saveFile(fileDto);
            return fileId;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

}
