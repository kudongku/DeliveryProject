package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.requestDto.CategoryRequestDto;
import com.sparta.deliveryproject.responseDto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getCategoryList();

    void createCategory(CategoryRequestDto categoryRequestDto);

    void editCategory(CategoryRequestDto categoryRequestDto, Long category_id);

    void deleteCategory(Long category_id);
}
