package com.example.crudproject.auth.controller;

import com.example.crudproject.auth.dto.CommentDto;
import com.example.crudproject.auth.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;



    @PostMapping
    public CommentDto createComment(
            @RequestBody CommentDto dto,
            @RequestParam("userId") Long userId,
            @RequestParam("articleId") Long articleId) {
        return commentService.createComment(articleId, userId, dto);
    }
}
