package com.yjh.book.springboot.web;

import com.yjh.book.springboot.config.auth.SecurityConfig;
import com.yjh.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자를 실행한다.
                             // SpringRunner.class는 스프링 부트 테스트와 JUnit 사이의 연결자 역할을 한다.
@WebMvcTest(controllers = HelloController.class, // Web(Spring MVC)에 집중할 수 있는 스프링 테스트 annotation으로, @Controller, @ControllerAdvice 등을 사용할 수 있지만 @Service, @Component, @Repository 등은 사용할 수 없다.
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 bean을 주입받는다.
    private MockMvc mvc; // 웹 API를 테스트할 때 사용하고, 스프링 MVC 테스트의 시작점이다. 이를 통해 HTTP GET, POST 등에 대한
                         // API 테스트를 할 수 있다.

    @Test
    @WithMockUser(roles = "USER")
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다. 체이닝이 지원되어 여러 검증 기능을 이어서 선언할 수 있다.
                .andExpect(status().isOk())  // HTTP Header의 Status를 검증한다.
                .andExpect(content().string(hello)); // mvc.perform의 결과를 검증한다.
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));

    }
}
