package com.blog_app.repositories;

import com.blog_app.entities.Category;
import com.blog_app.entities.Post;
import com.blog_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    @Query("select p from post where p.title like : key")
    List<Post> findByTitleContaining(@Param("key") String title);
}
