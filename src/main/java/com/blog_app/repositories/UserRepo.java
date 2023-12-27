package com.blog_app.repositories;

import com.blog_app.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
