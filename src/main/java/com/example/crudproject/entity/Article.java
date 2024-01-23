package com.example.crudproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;

    @Setter
    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "article")
    private final List<Comment> commentList = new ArrayList<>();

    public Article(String title, String content, Board board, User user) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.user = user;
    }
}
