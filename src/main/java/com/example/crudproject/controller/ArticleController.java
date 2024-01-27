package com.example.crudproject.controller;

import com.example.crudproject.dto.ArticleDto;
import com.example.crudproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    //create
    @PostMapping("/createArticle")
    public ArticleDto createArticle(
            @RequestBody
            ArticleDto dto,
            @RequestParam("userId")
            Long userId
    ){
        return articleService.createArticle(dto,userId);
    }
    //read
    @GetMapping("/{articleId}")
    public ArticleDto readArticle(
            @PathVariable("articleId")
            Long articleId
    ){
        return articleService.readArticleOne(articleId);
    }
    //update
    @PutMapping("/{articleId}/update")
    public ArticleDto updateArticle(
            @RequestBody
            ArticleDto dto,
            @RequestParam("userId")
            Long userId,
            @PathVariable("articleId")
            Long articleId
    ){
        return articleService.updateArticle(dto, userId, articleId);
    }
    //delete
    @DeleteMapping("/{articleId}/delete")
    public String deleteArticle(
            @RequestParam("userId")
            Long userId,
            @PathVariable("articleId")
            Long articleId
    ){
        return articleService.deleteArticle(userId, articleId);
    }
}
