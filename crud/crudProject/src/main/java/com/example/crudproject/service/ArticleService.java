package com.example.crudproject.service;

import com.example.crudproject.dto.ArticleDto;
import com.example.crudproject.entity.Article;
import com.example.crudproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


    //게시글 제목 검색
    public List<ArticleDto> serchArticles(String title) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        List<Article> articles = articleRepository.findByTitleContaining(title);


        for(Article article: articles){
            articleDtoList.add(ArticleDto.fromEntity(article));
        }
        return articleDtoList;
    }
}
