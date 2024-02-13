package com.blog_app.controllers;

import com.blog_app.entities.Post;
import com.blog_app.payloads.CategoryDto;
import com.blog_app.payloads.PostDto;
import com.blog_app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/p")
public class PostController {
    @Autowired
    private PostService postService;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId)
    {
        PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);

        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //get by user

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
    {

        List<PostDto> postDtos = this.postService.getPostByUser(userId);

        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    //get by category

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId)
    {

        List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);

        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/getAllPosts")

    public ResponseEntity<List<PostDto>> getAllPost(){

        List<PostDto> allPost = this.postService.getAllPost();

        return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
    }

    @GetMapping("/getSinglePosts/{postId}")

    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){

        PostDto singlePost = this.postService.getPostById(postId);

        return new ResponseEntity<PostDto>(singlePost,HttpStatus.OK);
    }


}
