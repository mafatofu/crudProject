package com.example.crudproject.auth.dto;

import lombok.Getter;

//로그인 사용자 정보를 받아오기 위한 dto
@Getter
public class JwtRequestDto {
    private String email;
    private String password;
}
