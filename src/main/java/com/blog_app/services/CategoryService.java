package com.blog_app.services;

import com.blog_app.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    //create
    public CategoryDto createCategory(CategoryDto categoryDto);
    //update
    public CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);
    //getSingleCategory
    public CategoryDto getCategoryById(Integer categoryId);
    //getAll
    public List<CategoryDto> getAllCategory();
    //delete
    public void deleteCategory(Integer categoryId);
}
