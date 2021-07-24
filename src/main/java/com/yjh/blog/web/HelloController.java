package com.yjh.blog.web;

import com.yjh.blog.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
public class HelloController {
    @GetMapping("/hello") // HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 준다.
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String // 외부에서 API로 넘긴 parameter를 가져오는 annotation이다.
                                                 name,
                                     @RequestParam("amount") int
                                             amount) {
        return new HelloResponseDto(name, amount);
    }
}


