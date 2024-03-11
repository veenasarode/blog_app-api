package com.blog_app.controllers;


import com.blog_app.config.AppConstants;
import com.blog_app.payloads.ApiResponse;
import com.blog_app.payloads.PostDto;
import com.blog_app.payloads.PostResponse;
import com.blog_app.services.FileService;
import com.blog_app.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/p")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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

    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value ="sortBy" ,  defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
                                                    @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR,  required = false) String sortDir)
    {

        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize, sortBy , sortDir);

        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/getSinglePosts/{postId}")

    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
    {

        PostDto singlePost = this.postService.getPostById(postId);

        return new ResponseEntity<PostDto>(singlePost,HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId)
    {
        this.postService.deletePost(postId);
        return new ApiResponse("Post successfully deleted!!" , true);
    }

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto updatePost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    //search
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords)
    {
       List<PostDto> searchResult = this.postService.searchPosts(keywords);
       return new ResponseEntity<List<PostDto>>(searchResult , HttpStatus.OK);
    }

    //post image
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage( @RequestParam ("image") MultipartFile image , @PathVariable Integer postId) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path , image);

        postDto.setImageName(fileName);

        PostDto updatedPost = this.postService.updatePost(postDto , postId);

        return new ResponseEntity<PostDto>(updatedPost , HttpStatus.OK);
    }

    //serve image

    @GetMapping(value = "/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName , HttpServletResponse response) throws IOException
    {
        InputStream resource  = this.fileService.getResource(path , imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource , response.getOutputStream());
    }

}
