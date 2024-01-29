package com.example.crudproject.auth.service;

import com.example.crudproject.auth.dto.ArticleDto;
import com.example.crudproject.auth.entity.Article;
import com.example.crudproject.auth.entity.Board;
import com.example.crudproject.auth.entity.User;
import com.example.crudproject.auth.repository.ArticleRepository;
import com.example.crudproject.auth.repository.BoardRepository;
import com.example.crudproject.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public ArticleDto createArticle(ArticleDto dto, Long userId){
        Optional<Board> board = boardRepository.findByCategory(dto.getCategory());
        Optional<User> user = userRepository.findById(userId);
        //카테고리 /유저 정보 체크
        //TODO (규칙 선정 이후) 예외처리 추가 필요
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저정보가 없습니다.");
        }
        if (board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시판이 없습니다.");
        }

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setBoard(board.get());
        article.setUser(user.get());

        return ArticleDto.fromEntity(articleRepository.save(article));
    }

    public Page<ArticleDto> readAllPagination(Integer page, Integer limit) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "id"));
        Page<Article> articlesEntityPage = articleRepository.findAll(pageable);
        return articlesEntityPage.map(ArticleDto::fromEntity);
    }

    public ArticleDto readArticleOne(Long articleId){
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        //TODO (규칙 선정 이후) 예외처리 추가 필요
        if (optionalArticle.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글이 존재하지 않습니다.");
        }
        return ArticleDto.fromEntity(optionalArticle.get());
    }

    public ArticleDto updateArticle(ArticleDto dto, Long userId, Long articleId){
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        Optional<Board> optionalBoard = boardRepository.findByCategory(dto.getCategory());
        Optional<User> optionalUser = userRepository.findById(userId);

        //TODO (규칙 선정 이후) 예외처리 추가 필요
        if (optionalArticle.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.");
        }
        if (optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저정보가 없습니다.");
        }
        if (optionalBoard.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시판이 없습니다.");
        }

        Article article = optionalArticle.get();
        Board board = optionalBoard.get();
        User user = optionalUser.get();

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setBoard(board);
        article.setUser(user);

        return ArticleDto.fromEntity(articleRepository.save(article));
    }

    public String deleteArticle(Long userId, Long articleId){
        //유저가 존재하지 않는다면
        if(!userRepository.existsById(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저정보가 없습니다.");
        }
        //글이 존재하지 않는다면
        if (!articleRepository.existsById(articleId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.");
        }

        articleRepository.deleteById(articleId);

        String answer = "삭제가 완료되었습니다";
        return answer;
    }

       public List<ArticleDto> serchArticles(String title) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        List<Article> articles = articleRepository.findByTitleContaining(title);


        for(Article article: articles){
            articleDtoList.add(ArticleDto.fromEntity(article));
        }
        return articleDtoList;
    }

}
