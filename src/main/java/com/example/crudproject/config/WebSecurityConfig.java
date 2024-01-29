package com.example.crudproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//security 설정을 하기 위한 Bean 객체
@Configuration
public class WebSecurityConfig {
    //스프링부트 시큐리티 관련 기능들을 모아둔 메서드
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{
        http
                //csrf 보안 해제.
                .csrf(AbstractHttpConfigurer::disable)
                // url에 따른 요청 인가
                .authorizeHttpRequests(auth -> auth
                        //별다른 인가 체크 없이 모두가 접근 가능
                        .requestMatchers(
                                "/home"
                        )
                        .permitAll()
                        //인가 체크 통과 시 접근 가능 페이지
                            //인가 체크 없이 모두가 접근 가능 + 리퀘스트 무관

                );
                // 보안 관련 세션 해제(JWT 이용 이유)

                // JWT 필터를 권한 필터 앞에 삽입
        return http.build();
    }
}
