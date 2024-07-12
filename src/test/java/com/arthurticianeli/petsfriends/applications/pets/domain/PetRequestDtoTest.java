package com.arthurticianeli.petsfriends.applications.pets.domain;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void shouldNotAllowCreationWithBlankNameCustomValidation() {

        PetRequestDto petRequestDto = new PetRequestDto(null, "Friendly dog", "http://example.com/dog.jpg", new CategoryEntity("Dog"), LocalDateTime.now(), PetStatus.AVAILABLE);

        Set<ConstraintViolation<PetRequestDto>> violations = validator.validate(petRequestDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotAllowCreationWithoutBirthDateCustomValidation() {

        PetRequestDto petRequestDto = new PetRequestDto("Buddy", "Friendly dog", "http://example.com/dog.jpg", new CategoryEntity("Dog"), null, PetStatus.AVAILABLE);

        Set<ConstraintViolation<PetRequestDto>> violations = validator.validate(petRequestDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotAllowCreationWithoutStatusCustomValidation() {

        PetRequestDto petRequestDto = new PetRequestDto("Buddy", "Friendly dog", "http://example.com/dog.jpg", new CategoryEntity("Dog"), LocalDateTime.now(), null);

        Set<ConstraintViolation<PetRequestDto>> violations = validator.validate(petRequestDto);

        assertFalse(violations.isEmpty());
    }
}