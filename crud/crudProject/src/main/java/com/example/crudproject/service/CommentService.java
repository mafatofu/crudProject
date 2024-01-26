package com.example.crudproject.service;

import com.example.crudproject.dto.CommentDto;
import com.example.crudproject.entity.Article;
import com.example.crudproject.entity.Comment;
import com.example.crudproject.entity.User;
import com.example.crudproject.repository.ArticleRepository;
import com.example.crudproject.repository.CommentRepository;
import com.example.crudproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    //댓글 작성
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
