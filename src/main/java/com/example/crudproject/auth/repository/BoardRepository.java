package com.example.crudproject.auth.repository;

import com.example.crudproject.auth.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByCategory(String category);
    boolean existsByCategory(String category);
}
