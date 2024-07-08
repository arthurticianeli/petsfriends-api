package com.arthurticianeli.petsfriends.applications.pets.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PetRequestDto(
        @NotBlank
        String name,
        String description,
        String urlImage,
        String category,
        @NotNull
        LocalDateTime birthDate,
        @NotNull
        String status
) {
}
