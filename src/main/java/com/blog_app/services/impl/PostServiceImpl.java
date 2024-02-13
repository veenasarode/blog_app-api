package com.blog_app.services.impl;

import com.blog_app.entities.Category;
import com.blog_app.entities.Post;
import com.blog_app.entities.User;
import com.blog_app.exceptions.ResourceNotFoundException;
import com.blog_app.payloads.PostDto;
import com.blog_app.repositories.CategoryRepo;
import com.blog_app.repositories.PostRepo;
import com.blog_app.repositories.UserRepo;
import com.blog_app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId" ,categoryId));

        Post post = this.modelMapper.map(postDto , Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {

        return null;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post singlePost = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId" ,postId));

        return this.modelMapper.map(singlePost, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPost() {

        List<Post> allPost = this.postRepo.findAll();

        List<PostDto> postDtos = allPost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId" ,postId));

        this.postRepo.delete(post);

    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId" ,categoryId));

        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return null;
    }
}
