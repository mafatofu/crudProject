package com.example.crudproject.controller;

import com.example.crudproject.dto.CommentDto;
import com.example.crudproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;



    @PostMapping
    public CommentDto createComment(@RequestBody CommentDto dto,
                                    @RequestParam("userId") Long userId,
                                    @RequestParam("articleId") Long articleId) {
        return commentService.createComment(articleId, userId, dto);
    }
}
