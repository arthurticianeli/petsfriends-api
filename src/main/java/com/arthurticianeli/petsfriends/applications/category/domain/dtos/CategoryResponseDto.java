package com.arthurticianeli.petsfriends.applications.category.domain.dtos;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;

import java.util.List;

public record CategoryResponseDto(
        Long id,
        String name
) {
    public CategoryResponseDto(CategoryEntity save) {
        this(save.getId(), save.getName());
    }

    public static List<CategoryResponseDto> from(List<CategoryEntity> all) {
        return all.stream().map(CategoryResponseDto::new).toList();
    }
}
