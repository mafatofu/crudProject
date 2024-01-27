package com.example.crudproject.dto;

import com.example.crudproject.entity.Article;
import com.example.crudproject.entity.Comment;
import com.example.crudproject.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;


import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @Email(message = "올바른 형식의 Email 주소를 입력해 주세요.")
    private String email;
    @Size(min = 4, max = 20)
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "비밀번호는 숫자, 대소문자, 특수문자를 포함하여 8자 이상이어야 합니다.")
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
