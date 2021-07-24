package com.yjh.blog.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 선언된 모든 field의 get method를 생성한다.(lombok)
@RequiredArgsConstructor // 선언된 모든 final field가 포함된 생성자를 생성해 준다. final이 없는 field는 포함되지 않는다.(lombok)
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
