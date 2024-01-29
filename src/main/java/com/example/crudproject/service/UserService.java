package com.example.crudproject.service;

import com.example.crudproject.dto.UserDto;
import com.example.crudproject.entity.User;
import com.example.crudproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 가입
    public UserDto create(UserDto dto) {

        // 중복 email 가입 불가 (User의 ID를 email로 사용 간주)
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("이미 가입된 Email입니다.");

        User newUser = new User(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword()
        );
        return UserDto.fromEntity(userRepository.save(newUser));
    }

    // 비밀번호 변경
    public UserDto updatePassword(Long id, String email, String password, String newPassword) {
        // PK로 User 찾기
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User targetUser = optionalUser.get();
            // 입력 email과 password 검증
            if (targetUser.getEmail().equals(email)
                    && targetUser.getPassword().equals(password)) {
                targetUser.setPassword(newPassword);
                return UserDto.fromEntity(userRepository.save(targetUser));
            } else if (!targetUser.getEmail().equals(email)) {
                // 입력 Email 불일치
                throw new IllegalArgumentException("입력하신 Email을 찾을 수 없습니다.");
            } else {
                // 입력 비밀번호 불일치
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
        throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    // 회원 탈퇴
    // TODO 검증 로직 작동하지 않아서 롤백..
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }



}
