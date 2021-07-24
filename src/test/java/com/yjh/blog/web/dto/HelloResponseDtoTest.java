package com.yjh.blog.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name); // assertj라는 테스트 검증 library의 검증 method로 검증 대상을 parameter로 받는다. 메소드 체이닝 지원
        assertThat(dto.getAmount()).isEqualTo(amount); // isEqualTo는 assertj의 동등 비교 method로 assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공이다.
    }
}
