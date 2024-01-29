package com.example.crudproject.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class SecurityTestController {

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login-form";
    }

    @PostMapping("/login")
    public String login(){
        return "my-profile";
    }

    @GetMapping("/my-page")
    public String myPage(){
        return "my-profile";
    }

    @GetMapping("/register")
    public String signUpForm(){
        return "register-form";
    }
}
