package com.example.crudproject.controller;

import com.example.crudproject.dto.ArticleDto;
import com.example.crudproject.dto.BoardDto;
import com.example.crudproject.service.ArticleService;
import com.example.crudproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ArticleService articleService;

    /*
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
    */

    /*
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
     */


    @ResponseBody
    @GetMapping
    public Map<String, Object> listAllBoardsPaginationApi(
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "limit", defaultValue = "5")
            Integer limit
    ) {
        List<BoardDto> boards = boardService.readAllTitleAndId();
        Page<ArticleDto> articleDtoPage = articleService.readAllPagination(page, limit);

        Map<String, Object> apiResponseMap = new HashMap<>();
        apiResponseMap.put("boards", boards);
        apiResponseMap.put("articleDtoPage", articleDtoPage);
        return apiResponseMap;
    }

    @ResponseBody
    @GetMapping("{boardId}")
    public Map<String, Object> listOneBoardApi(
            @PathVariable("boardId")
            Long boardId,
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "limit", defaultValue = "5")
            Integer limit
    ) {

        Page<ArticleDto> articleDtoPage = boardService.readBoardArticlePage(boardId, page, limit);
        List<BoardDto> boards = boardService.readAllTitleAndId();
        BoardDto selected = boardService.readTitleAndId(boardId);

        Map<String, Object> apiResponseMap = new HashMap<>();
        apiResponseMap.put("boards", boards);
        apiResponseMap.put("selected", selected);
        apiResponseMap.put("articleDtoPage", articleDtoPage);

        return apiResponseMap;
    }
}
