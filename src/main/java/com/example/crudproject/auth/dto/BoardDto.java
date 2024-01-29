package com.example.crudproject.auth.dto;

import com.example.crudproject.auth.entity.Article;
import com.example.crudproject.auth.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String category;
    private final List<ArticleDto> articleDtoList = new ArrayList<>();

    public BoardDto(String category) {
        this.category = category;
    }

    public static BoardDto fromEntity(Board entity){
        BoardDto dto = new BoardDto();
        dto.id = entity.getId();
        dto.category = dto.getCategory();
        for (Article article : entity.getArticleList()){
            dto.articleDtoList.add(ArticleDto.fromEntity(article));
        }
        return dto;
    }

    public static BoardDto fromEntityByTitleAndId(Board entity) {
        BoardDto dto = new BoardDto();
        dto.id = entity.getId();
        dto.category = entity.getCategory();
        return dto;
    }
}
