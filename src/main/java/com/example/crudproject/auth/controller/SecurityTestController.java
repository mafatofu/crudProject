package com.example.crudproject.auth.controller;

import com.example.crudproject.auth.dto.JwtRequestDto;
import com.example.crudproject.auth.dto.JwtResponseDto;
import com.example.crudproject.auth.entity.CustomUserDetails;
import com.example.crudproject.auth.jwt.JwtTokenUtils;
import com.example.crudproject.auth.service.JpaUserDetailManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class SecurityTestController {
    // JWT를 발급하기 위한 Bean
    private final JwtTokenUtils jwtTokenUtils;
    // 사용자 정보를 회수하기 위한 Bean
    private final JpaUserDetailManager manager;
    // 사용자가 제공한 아이디 비밀번호를 비교하기 위한 클래스
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home(){
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());

        return "index";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login-form";
    }

    @PostMapping("/issue")
    public JwtResponseDto login(
            @RequestBody
            JwtRequestDto dto
    ){
        //사용자가 제공한 username, password가 저장된 사용자인지 체크
        if(!manager.userExistsByEmail(dto.getEmail()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        UserDetails userDetails
                = manager.loadUserByEmail(dto.getEmail());

        if (!passwordEncoder
                .matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // JWT 발급
        String jwt = jwtTokenUtils.generateToken((CustomUserDetails) userDetails);
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;
    }

    @GetMapping("/validate")
    public Claims validateToken(
            @RequestParam("token")
            String token
    ) {
        if (!jwtTokenUtils.validate(token))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return jwtTokenUtils.parseClaims(token);
    }


    // 회원가입 화면
    @GetMapping("/register")
    public String signUpForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String signUpRequest(
            //추후 이메일로 변경
            //프론트단에서 username으로 받아오기에, 일단 값만 받음
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password,
            @RequestParam("password-check")
            String passwordCheck
    ) {
        if (password.equals(passwordCheck))
            manager.createUserByEmail(
                    CustomUserDetails.builder()
                    .email(username)
                    .password(passwordEncoder.encode(password))
                    .build());

        // 회원가입 성공 후 로그인 페이지로
        return "redirect:/token/login";
    }

    // 로그인 한 후에 내가 누군지 확인하기 위한
    @GetMapping("/my-profile")
    public String myProfile(
            Authentication authentication,
            Model model
    ) {
        model.addAttribute("email", ((CustomUserDetails)authentication.getPrincipal()).getEmail());
        log.info(((CustomUserDetails) authentication.getPrincipal()).getEmail());
        log.info(((CustomUserDetails) authentication.getPrincipal()).getPassword());
        return "my-profile";
    }

}


