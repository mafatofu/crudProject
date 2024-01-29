package com.example.crudproject.controller;

import com.example.crudproject.dto.UserDto;
import com.example.crudproject.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated // 유효성 검사 적용
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public UserDto create(
            @Valid
            @RequestBody
            UserDto dto
    ) {
        log.info(dto.toString());
        return userService.create(dto);
    }

    @PutMapping("/{userId}/changePw")
    public UserDto updatePassword(
            @PathVariable("userId")
            Long userId,
            @Email
            @RequestParam("email")
            String email,
            @RequestParam("password")
            String password,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                    message = "비밀번호는 숫자, 대소문자, 특수문자를 포함하여 8자 이상이어야 합니다.")
            @RequestParam("newPassword")
            String newPassword
    ) {
        return userService.updatePassword(userId, email, password, newPassword);
    }

    // TODO 검증 로직 고장나서 롤백..
    // 삭제 확인 안 됨..
    @DeleteMapping("/{userId}/delete")
    public void deleteUser(
            @PathVariable("userId")
            Long userId
    ) {
        userService.deleteUser(userId);
    }

}
