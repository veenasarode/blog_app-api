package com.blog_app.services.impl;

import com.blog_app.entities.Category;

import com.blog_app.exceptions.ResourceNotFoundException;
import com.blog_app.payloads.CategoryDto;

import com.blog_app.repositories.CategoryRepo;
import com.blog_app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.dtoToCategory(categoryDto);
        Category saveCategory = this.categoryRepo.save(category);
        return this.categoryToDto(saveCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId" ,categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCat = this.categoryRepo.save(category);
        CategoryDto categoryDto1 = this.categoryToDto(updatedCat);

        return categoryDto1;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId" ,categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories = this.categoryRepo.findAll();

        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());

        return categoryDtos;

    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId" ,categoryId));

        this.categoryRepo.delete(category);

    }

    private Category dtoToCategory(CategoryDto categoryDto)
    {
        Category category = this.modelMapper.map(categoryDto , Category.class);
        return category;
    }

    private CategoryDto categoryToDto(Category category)
    {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
