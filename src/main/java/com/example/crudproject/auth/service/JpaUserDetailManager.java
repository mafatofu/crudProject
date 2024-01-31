package com.example.crudproject.auth.service;

import com.example.crudproject.auth.entity.CustomUserDetails;
import com.example.crudproject.auth.entity.User;
import com.example.crudproject.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class JpaUserDetailManager implements UserDetailsManager {
    private final UserRepository userRepository;

    public JpaUserDetailManager(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    //이메일명으로 사용자 데이터 체크
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    //이메일명으로 사용자 데이터 불러오기
    public UserDetails loadUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new IllegalArgumentException("입력하신 Email을 찾을 수 없습니다.");
        }
        User user = optionalUser.get();
        return CustomUserDetails.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    //이메일로 회원가입
    //TODO 관리자에 대한 부분은 추후 작성
    //아이디가 아닌 이메일로 회원가입함에 따라, CustomUserDetails로 매개변수를 받는다.
    public void createUserByEmail(CustomUserDetails user) {
        //이메일 중복 체크
        if (userExistsByEmail(user.getEmail()))
            throw new IllegalArgumentException("이미 가입된 Email입니다.");
        try{
            //직접 작성한 유저정보클래스로 사용자 정보 받기
            //user : CustomUserDetails
            //createUser : User entity
            CustomUserDetails userDetails = (CustomUserDetails) user;
            User createUser = new User();
            //TODO 추후 Builder로 변경 고민
            createUser.setEmail(user.getEmail());
            //createUser.setUsername(user.getUsername());
            createUser.setPassword(user.getPassword());
            userRepository.save(createUser);
        } catch (ClassCastException e){//클래스 변경부분에 대한 예외
            //CustomUserDetails 확인 필요
            log.error("Failed Cast to : {}", CustomUserDetails.class);
            //개발자가 잘못했다
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void createUser(UserDetails user) {

    }
    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }



    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }


}
