package com.example.crudproject.controller;

import com.example.crudproject.dto.ArticleDto;
import com.example.crudproject.dto.BoardDto;
import com.example.crudproject.service.ArticleService;
import com.example.crudproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ArticleService articleService;

    @GetMapping
    public String listAllBoardsPagination(
            Model model,
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "limit", defaultValue = "20")
            Integer limit
    ) {
        model.addAttribute("boards", boardService.readAll());
        model.addAttribute("selected", null);
        Page<ArticleDto> articleDtoPage = articleService.readAllPagination(page, limit);
        model.addAttribute("articles", articleDtoPage);
        return "board";
    }

    @GetMapping("{boardId}")
    public String listOneBoard(
            @PathVariable("boardId")
            Long boardId,
            Model model,
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "limit", defaultValue = "20")
            Integer limit
    ) {
        BoardDto boardDto = boardService.read(boardId);
        model.addAttribute("boards", boardService.readAll());
        model.addAttribute("selected", boardDto);

        List<ArticleDto> articles = boardDto.getArticleDtoList();
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "id"));
        Page<ArticleDto> articleDtoPage = new PageImpl<>(articles, pageable, articles.size());

        model.addAttribute("articles", articleDtoPage);
        return "board";
    }
}
