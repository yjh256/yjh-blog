package com.yjh.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 원래는 Application.class에서 이 annotation을 사용했으나 WebMvcTest에서 Entity 클래스를 가지고 있지 않는 문제 때문에
// JpaConfig로 분리한다.
public class JpaConfig {}
