package com.example.crudproject.auth.dto;

import com.example.crudproject.auth.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;

    public CommentDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static CommentDto fromEntity(Comment entity){
        CommentDto dto = new CommentDto();
        dto.id = entity.getId();
        dto.content = entity.getContent();
        return dto;
    }
}
