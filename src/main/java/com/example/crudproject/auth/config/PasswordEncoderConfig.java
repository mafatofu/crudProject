package com.example.crudproject.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//사용자 비밀번호 암호화 관련 클래스
//순환참조 발생 이슈 방지를 위해 class 따로 제작
@Configuration
public class PasswordEncoderConfig {
    //비밀번호 암호화 메서드 
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
