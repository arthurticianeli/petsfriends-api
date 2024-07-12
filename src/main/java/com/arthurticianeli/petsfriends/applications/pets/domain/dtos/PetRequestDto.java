package com.arthurticianeli.petsfriends.applications.pets.domain.dtos;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PetRequestDto(
        @NotBlank
        String name,
        String description,
        String urlImage,
        CategoryEntity category,
        @NotNull
        LocalDateTime birthDate,
        @NotNull
        PetStatus status
) {
}
