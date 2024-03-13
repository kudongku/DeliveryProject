package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.requestDto.CategoryRequestDto;
import com.sparta.deliveryproject.responseDto.CategoryResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @Test
    void getCategoryListTest() {
        Category category = new Category("Chinese", "중국집");
        Category category2 = new Category("Japanese", "일식집");

        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);

        given(categoryRepository.findAll()).willReturn(List.of(category, category2));

        List<CategoryResponseDto> categories = categoryService.getCategoryList();

        assert (categories.size() == 2);
    }

    @Test
    void createCategoryTest() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Korean", "한식");
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
        categoryService.createCategory(categoryRequestDto);
    }

    @Test
    void editCategoryTest() {
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Korean", "한식");
        Category category = new Category("Korean2", "한식2");

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        categoryService.editCategory(categoryRequestDto, 1L);
    }

    @Test
    void deleteCategoryTest() {
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
        Category category = new Category("Korean2", "한식2");

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        categoryService.deleteCategory(1L);
    }
}