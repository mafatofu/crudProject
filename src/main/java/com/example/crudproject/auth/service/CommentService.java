package com.example.crudproject.auth.service;

import com.example.crudproject.auth.dto.CommentDto;
import com.example.crudproject.auth.entity.Article;
import com.example.crudproject.auth.entity.Comment;
import com.example.crudproject.auth.entity.User;
import com.example.crudproject.auth.repository.ArticleRepository;
import com.example.crudproject.auth.repository.CommentRepository;
import com.example.crudproject.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentDto createComment(Long articleId,
                              Long userId,
                              CommentDto dto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow();
        User user = userRepository.findById(userId)
                .orElseThrow();
        Comment comment = new Comment(
                dto.getContent(), article,user
        );

      return  CommentDto.fromEntity(commentRepository.save(comment));
    }
}
