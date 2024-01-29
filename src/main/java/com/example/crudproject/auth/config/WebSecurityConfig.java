package com.example.crudproject.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
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
                        .requestMatchers(
                                "/my-page"
                        )
                        .authenticated()
                        //인가 체크 부분
                        .requestMatchers(
                                "/login",
                                "/register"
                        )
                        .anonymous()
                        .anyRequest()
                        .permitAll()

                );
                // 보안 관련 세션 해제(JWT 이용 이유)

                // JWT 필터를 권한 필터 앞에 삽입
                
                // + 로그아웃에 대한 부분 체크해보기(토큰삭제)
        return http.build();
    }
    //테스트를 위한 임시 클래스. 추후 삭제 예정
    @Bean
    public UserDetailsManager userDetailsManager(
            PasswordEncoder passwordEncoder
    ) {
        // 사용자 1
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder.encode("as"))
                .build();
        // Spring Security에서 기본으로 제공하는,
        // 메모리 기반 사용자 관리 클래스 + 사용자 1
        return new InMemoryUserDetailsManager(user1);
    }
}
