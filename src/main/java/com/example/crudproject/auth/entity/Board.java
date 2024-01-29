package com.example.crudproject.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String category;

    @OneToMany(mappedBy = "board")
    private final List<Article> articleList = new ArrayList<>();

    public Board(String category) {
        this.category = category;
    }
}
