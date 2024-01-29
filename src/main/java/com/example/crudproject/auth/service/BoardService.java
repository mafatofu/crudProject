package com.example.crudproject.auth.service;

import com.example.crudproject.auth.dto.ArticleDto;
import com.example.crudproject.auth.dto.BoardDto;
import com.example.crudproject.auth.entity.Board;
import com.example.crudproject.auth.repository.BoardRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private static final String[] basicBoardCategory = {
            "전체",
            "공지",
            "일반",
            "질문",
            "정보"
    };

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
        for (String boardCategory : basicBoardCategory) {
            if (!this.boardRepository.existsByCategory(boardCategory)) {
                Board board = new Board();
                board.setCategory(boardCategory);
                this.boardRepository.save(board);
            }
        }
    }

    public List<BoardDto> readAll() {
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardRepository.findAll()) {
            boardDtos.add(BoardDto.fromEntity(board));
        }
        return boardDtos;
    }

    public BoardDto read(Long boardId) {
        return BoardDto.fromEntity(boardRepository.findById(boardId).orElseThrow());
    }

    // 필요한 정보만 set하는 메서드
    public List<BoardDto> readAllTitleAndId() {
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardRepository.findAll()) {
            boardDtos.add(BoardDto.fromEntityByTitleAndId(board));
        }
        return boardDtos;
    }

    // 필요한 정보만 set하는 메서드
    public BoardDto readTitleAndId(Long boardId) {
        return BoardDto.fromEntityByTitleAndId(boardRepository.findById(boardId).orElseThrow());
    }

    public Page<ArticleDto> readBoardArticlePage(Long boardId, Integer page, Integer limit) {

        BoardDto boardDto = read(boardId);
        List<ArticleDto> articles = boardDto.getArticleDtoList();
        // ID 역정렬 안됨
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "id"));
        // Collections으로 역정렬
        Collections.reverse(articles);

        // 주어진 pageable을 사용하여 리스트를 페이징
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), articles.size());
        List<ArticleDto> pagedArticles = articles.subList(start, end);

        // PageImpl을 사용하여 Page 객체 생성
        Page<ArticleDto> articleDtoPage = new PageImpl<>(pagedArticles, pageable, articles.size());

        return articleDtoPage;
    }
}
