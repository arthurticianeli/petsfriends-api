package com.arthurticianeli.petsfriends.applications.pets.domain;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Pet Test")
public class PetTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should create pet valid")
    public void shouldCreatePetValid() {

        var localDateTime = LocalDateTime.now();
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.AVAILABLE
        );


        PetEntity petEntity = new PetEntity(petRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Dog", petEntity.getName()),
                () -> Assertions.assertEquals("Dog", petEntity.getCategory().getName()),
                () -> Assertions.assertEquals("Dog", petEntity.getUrlImage()),
                () -> Assertions.assertEquals(localDateTime, petEntity.getBirthDate()),
                () -> Assertions.assertEquals(PetStatus.AVAILABLE, petEntity.getStatus())
        );
    }

    @Test
    @DisplayName("Should not create pet without name")
    public void shouldNotCreatePetWithoutName() {
        var localDateTime = LocalDateTime.now();

        PetRequestDto petRequestDto = new PetRequestDto(
                null, // Name is null
                "Dog description",
                "Dog URL",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        Set<ConstraintViolation<PetEntity>> violations = validator.validate(petEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should not create pet without category")
    public void shouldNotCreatePetWithoutCategory() {
        var localDateTime = LocalDateTime.now();

        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog description",
                "Dog URL",
                null,
                localDateTime,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        Set<ConstraintViolation<PetEntity>> violations = validator.validate(petEntity);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should not create pet without birth date")
    public void shouldNotCreatePetWithoutBirthDate() {

        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog description",
                "Dog URL",
                new CategoryEntity("Dog"),
                null,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        Set<ConstraintViolation<PetEntity>> violations = validator.validate(petEntity);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should not create pet without status")
    public void shouldNotCreatePetWithoutStatus() {
        var localDateTime = LocalDateTime.now();

        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog description",
                "Dog URL",
                new CategoryEntity("Dog"),
                localDateTime,
                null
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        Set<ConstraintViolation<PetEntity>> violations = validator.validate(petEntity);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Update pet")
    public void shouldUpdatePet() {
        var localDateTime = LocalDateTime.now();
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        petEntity.update(new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.ADOPTED
        ));

        Assertions.assertEquals(PetStatus.ADOPTED, petEntity.getStatus());
    }

    @Test
    @DisplayName("Equals pet")
    public void shouldEqualsPet() {
        var localDateTime = LocalDateTime.now();
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);
        PetEntity petEntity2 = new PetEntity(petRequestDto);

        Assertions.assertEquals(petEntity, petEntity2);
    }

    @Test
    @DisplayName("Update status")
    public void shouldUpdateStatus() {
        var localDateTime = LocalDateTime.now();
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        petEntity.updateStatus(PetStatus.ADOPTED);

        Assertions.assertEquals(PetStatus.ADOPTED, petEntity.getStatus());
    }

    @Test
    @DisplayName("Calculate age")
    public void shouldCalculateAge() {
        var localDateTime = LocalDateTime.now();
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                localDateTime,
                PetStatus.AVAILABLE
        );

        PetEntity petEntity = new PetEntity(petRequestDto);

        Assertions.assertEquals(0, petEntity.calculateAge());
    }

    @Test
    @DisplayName("Should create pet with valid date at created at")
    public void shouldCreatePetWithValidDateAtCreatedAt() {
        var localDateTime = LocalDateTime.now();
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                null,
                localDateTime,
                PetStatus.AVAILABLE
        );


        PetEntity petEntity = new PetEntity(petRequestDto);
        petEntity.setCategory(new CategoryEntity("Dog"));

        Assertions.assertNotNull(petEntity.getCreatedAt());
    }
}
