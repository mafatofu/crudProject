package com.example.crudproject.service;

import com.example.crudproject.dto.UserDto;
import com.example.crudproject.entity.User;
import com.example.crudproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        // 중복 email 가입 불가 (id로 email 사용 간주)
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");

        User newUser = new User(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword()
        );
        return UserDto.fromEntity(userRepository.save(newUser));
    }

    // 비밀번호 변경
    // TODO id로 user를 찾고 email과 password 일치를 검증해야 하는지,
    // TODO id 없이 입력한 email과 password로만 user를 찾아서 해야 하는지 헷갈림
    public UserDto updatePassword(Long id, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isPresent()) {
            User targetUser = optionalUser.get();
            targetUser.setPassword(password);
            return UserDto.fromEntity(userRepository.save(targetUser));
        }
        // TODO email이 없는지, password 불일치인지 구별하여 예외 던지도록 수정해야함
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }
}
