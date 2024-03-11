package com.blog_app.services;

import com.blog_app.entities.Post;
import com.blog_app.payloads.PostDto;
import com.blog_app.payloads.PostResponse;
import jakarta.persistence.criteria.CriteriaBuilder;


import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    public PostDto updatePost(PostDto postDto, Integer postId);
    public PostDto getPostById(Integer postId);
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize , String sortBy ,String sortDir);
    public void deletePost(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);
}
