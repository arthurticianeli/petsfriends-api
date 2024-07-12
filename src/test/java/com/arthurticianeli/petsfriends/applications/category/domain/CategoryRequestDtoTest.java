package com.arthurticianeli.petsfriends.applications.category.domain;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void shouldCreateCategoryRequestDtoWithName() {
        String name = "Dog";

        CategoryRequestDto dto = new CategoryRequestDto(name);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(name, dto.name());
    }

    @Test
    public void shouldNotAllowCreationWithBlankNameCustomValidation() {
        String name = " ";

        CategoryRequestDto dto = new CategoryRequestDto(name);

        var violations = validator.validate(dto);

        Assertions.assertFalse(violations.isEmpty());
    }
}