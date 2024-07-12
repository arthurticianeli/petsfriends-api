package com.arthurticianeli.petsfriends.applications.category.domain;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryResponseDto;
import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryResponseDtoTest {

    @Test
    public void testConstructor() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Test Category");

        CategoryResponseDto dto = new CategoryResponseDto(categoryEntity);

        assertEquals(categoryEntity.getId(), dto.id());
        assertEquals(categoryEntity.getName(), dto.name());
    }
}