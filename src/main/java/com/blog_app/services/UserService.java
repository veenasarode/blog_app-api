package com.blog_app.services;

import com.blog_app.payloads.UserDto;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto user);
    public UserDto updateUser(UserDto user, Integer userId);
    public UserDto getUserById(Integer userId);
    public List<UserDto> getAllUser();
    public void deleteUser(Integer userId);
}
