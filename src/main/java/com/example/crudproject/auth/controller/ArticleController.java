package com.example.crudproject.auth.controller;

import com.example.crudproject.auth.dto.ArticleDto;
import com.example.crudproject.auth.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    //create
    @PostMapping("/create")
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
    // TODO 삭제 폼은 별도의 view 없이 버튼에 넣어 삭제 완료되면 home이나
    // TODO 이전에 보던 페이지로 리다이렉트 하는 것 어떠신지요..
    @DeleteMapping("/{articleId}/delete")
    public String deleteArticle(
            @RequestParam("userId")
            Long userId,
            @PathVariable("articleId")
            Long articleId
    ){
        return articleService.deleteArticle(userId, articleId);
    }

     //search
     @GetMapping("/search")
     public List<ArticleDto> searchArticles(
        @RequestParam("title")                           
        String title
    ){
        return articleService.serchArticles(title);
    }

    
}
