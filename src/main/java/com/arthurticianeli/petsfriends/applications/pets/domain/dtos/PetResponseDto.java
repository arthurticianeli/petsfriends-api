package com.arthurticianeli.petsfriends.applications.pets.domain.dtos;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;

import java.time.LocalDateTime;

public record PetResponseDto(
        Long id,
        String name,
        String description,
        String urlImage,
        CategoryEntity category,
        LocalDateTime birthDate,
        Number age,
        PetStatus status
) {
    public PetResponseDto(PetEntity pet) {
        this(
                pet.getId(),
                pet.getName(),
                pet.getDescription() != null ? pet.getDescription() : "",
                pet.getUrlImage() != null ? pet.getUrlImage() : "",
                pet.getCategory() != null ? pet.getCategory() : null,
                pet.getBirthDate(),
                pet.calculateAge(),
                pet.getStatus());
    }
}
