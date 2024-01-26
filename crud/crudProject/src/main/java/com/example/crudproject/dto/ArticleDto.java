package com.example.crudproject.dto;

import com.example.crudproject.entity.Article;
import com.example.crudproject.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private final List<CommentDto> commentDtoList = new ArrayList<>();

    public ArticleDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public static ArticleDto fromEntity(Article entity){
        ArticleDto dto = new ArticleDto();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.content = entity.getContent().replace("\n", "<br>");
        dto.category = entity.getBoard().getCategory();
        for(Comment comment : entity.getCommentList()){
            dto.commentDtoList.add(CommentDto.fromEntity(comment));
        }
        return dto;
    }
}
