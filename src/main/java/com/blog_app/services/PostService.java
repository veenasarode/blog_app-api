package com.blog_app.services;

import com.blog_app.entities.Post;
import com.blog_app.payloads.PostDto;


import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    public Post updatePost(PostDto postDto, Integer postId);
    public PostDto getPostById(Integer postId);
    public List<PostDto> getAllPost();
    public void deletePost(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<Post> searchPosts(String keyword);
}
