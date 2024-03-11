package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.requestDto.CategoryRequestDto;
import com.sparta.deliveryproject.responseDto.CategoryResponseDto;
import com.sparta.deliveryproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getCategoryList();
        return ResponseEntity.status(200).body(categoryResponseDtoList);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/categories")
    public void createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.createCategory(categoryRequestDto);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/categories/{categoryId}")
    public void editCategory(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable Long categoryId) {
        categoryService.editCategory(categoryRequestDto, categoryId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("categories/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

}
