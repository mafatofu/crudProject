package com.example.crudproject.auth.repository;

import com.example.crudproject.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // email 중복 확인
    boolean existsByEmail(String email);

    // 가입시 입력한 email과 password로 User 정보 찾기
    Optional<User> findByEmailAndPassword(String email, String password);

    //이메일로 사용자 찾기
    Optional<User> findByEmail(String email);
}
