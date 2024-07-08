package com.arthurticianeli.petsfriends.applications.pets.domain.dtos;

import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;

public record PetRequestFilterDto (
        String name,
        String category,
        String status
){}