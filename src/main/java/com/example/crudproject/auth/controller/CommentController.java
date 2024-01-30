package com.example.crudproject.auth.controller;

import com.example.crudproject.auth.dto.CommentDto;
import com.example.crudproject.auth.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article/{articleId}")
public class CommentController {

    private final CommentService commentService;


    // TODO @RequestMapping ("/article/{articleId}") 추가하여 수정 부분 있습니다
    @PostMapping
    public CommentDto createComment(
            @RequestBody CommentDto dto,
            @RequestParam("userId") Long userId,
            @PathVariable("articleId") Long articleId
    )
            {
        return commentService.createComment(articleId, userId, dto);
    }
}
