package com.example.crudproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String email;
    @Setter
    private String username;
    @Setter
    private String password;

    @OneToMany(mappedBy = "user")
    private final List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Comment> commentList = new ArrayList<>();

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
