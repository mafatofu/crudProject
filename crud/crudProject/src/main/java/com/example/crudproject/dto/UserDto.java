package com.example.crudproject.dto;

import com.example.crudproject.entity.Article;
import com.example.crudproject.entity.Comment;
import com.example.crudproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;

    private final List<ArticleDto> articleDtoList = new ArrayList<>();
    private final List<CommentDto> commentDtoList = new ArrayList<>();

    public UserDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public static UserDto fromEntity(User entity){
        UserDto dto = new UserDto();
        dto.id = entity.getId();
        dto.email = entity.getEmail();
        dto.username = entity.getUsername();
        dto.password = entity.getPassword();

        for (Article article : entity.getArticleList()){
            dto.articleDtoList.add(ArticleDto.fromEntity(article));
        }
        for(Comment comment : entity.getCommentList()){
            dto.commentDtoList.add(CommentDto.fromEntity(comment));
        }
        return dto;
    }


}
