package com.arthurticianeli.petsfriends.applications.category.domain.service;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICategoryService {
    CategoryResponseDto saveCategory(CategoryRequestDto petRequestDto);

    void deleteCategory(Long id);

    Page<CategoryResponseDto> findAllCategoriesPaginated(Pageable pageable);

    List<CategoryResponseDto> getAllCategories();
}
