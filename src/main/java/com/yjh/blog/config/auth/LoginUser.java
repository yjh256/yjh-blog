package com.yjh.blog.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 이 annotation이 생성될 수 있는 위치를 지정한다. PARAMETER로 지정했으니 method의 parameter로 선언된 객체에서만 사용할 수 있다.
// 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있다.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // @interface는 이 파일을 annotation 클래스로 지정한다. 즉, LoginUser라는 이름을 가진 annotation이 생성되었다.
}
