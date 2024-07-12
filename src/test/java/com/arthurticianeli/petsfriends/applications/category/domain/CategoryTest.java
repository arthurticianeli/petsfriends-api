package com.arthurticianeli.petsfriends.applications.category.domain;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Category Test")
public class CategoryTest {

    @Test
    @DisplayName("Should create category valid")
    public void shouldCreateCategoryValid(){
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                "Dog"
        );
        CategoryEntity categoryEntity = new CategoryEntity(categoryRequestDto);
        assert categoryEntity.getName().equals("Dog");
    }

    @Test
    @DisplayName("Should not create category without name")
    public void shouldNotCreateCategoryWithoutName(){
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                null
        );
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new CategoryEntity(categoryRequestDto));
        org.junit.jupiter.api.Assertions.assertTrue(exception.getMessage().contains("Name is required"));
    }
}
