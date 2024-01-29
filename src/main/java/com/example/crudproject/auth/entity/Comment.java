package com.example.crudproject.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String content;

    @Setter
    @ManyToOne
    private Article article;

    @ManyToOne
    private User user;

    public Comment(String content, Article article,User user) {
        this.content = content;
        this.article = article;
        this.user = user;
    }
}
