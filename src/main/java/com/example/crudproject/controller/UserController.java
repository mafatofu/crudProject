package com.example.crudproject.controller;

import com.example.crudproject.dto.UserDto;
import com.example.crudproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public UserDto create(
            @RequestBody
            UserDto dto
    ) {
        log.info(dto.toString());
        return userService.create(dto);
    }

    @PutMapping("/{userId}/edit")
    public UserDto updatePassword(
            @PathVariable("userId")
            Long userId,
            @RequestParam("email")
            String email,
            @RequestParam("password")
            String password,
            @RequestParam("newPassword")
            String newPassword
    ) {
        return userService.updatePassword(userId, email, password, newPassword);
    }
}
