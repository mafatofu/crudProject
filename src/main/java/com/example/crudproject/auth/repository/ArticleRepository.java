

package com.example.crudproject.auth.repository;

import com.example.crudproject.auth.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContaining(String title);
    List<Article> findByContentContaining(String content);

}
