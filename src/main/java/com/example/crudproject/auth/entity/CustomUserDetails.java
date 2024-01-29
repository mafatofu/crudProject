package com.example.crudproject.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

//UserEntity를 바탕으로 Spring Security 내부에서
//사용자 정보를 주고받기 위한 객체임을 나타내는 interface UserDetails의
//커스텀 구현체
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private Long id;
    private String username;
    private String password;
    @Getter
    private String email;

    //권한관련부분
    //TODO null 반환 불가. 추후 수정. 일단 빈 컬렉션 형태 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of();
    }
    
    //UserDetails 구현 메서드
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
