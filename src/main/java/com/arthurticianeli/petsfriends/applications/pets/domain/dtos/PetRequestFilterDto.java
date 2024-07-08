package com.arthurticianeli.petsfriends.applications.pets.domain.dtos;

public record PetRequestFilterDto(
        String name,
        String category,
        String status
) {
}