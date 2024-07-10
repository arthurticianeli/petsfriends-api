package com.arthurticianeli.petsfriends.applications.category.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank
        String name
) {
}
