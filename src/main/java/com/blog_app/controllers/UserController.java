package com.blog_app.controllers;


import com.blog_app.payloads.ApiResponse;
import com.blog_app.payloads.UserDto;
import com.blog_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
   @Autowired
    private UserService userService;

    //POST - create user
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
       UserDto createdUserDto = this.userService.createUser(userDto);
       return new ResponseEntity<>(createdUserDto , HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId)
    {
       UserDto updatedUserDto = this.userService.updateUser(userDto , userId);
       return ResponseEntity.ok(updatedUserDto);
    }

    //DELETE - delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId)
    {
          this.userService.deleteUser(userId);
          return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }
    //GET - get user
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/getSingle/{userId}")
    public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
