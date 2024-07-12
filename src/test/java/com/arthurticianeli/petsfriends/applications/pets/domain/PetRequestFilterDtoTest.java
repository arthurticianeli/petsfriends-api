package com.arthurticianeli.petsfriends.applications.pets.domain;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestFilterDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PetRequestFilterDtoTest {

    @Test
    public void shouldCreatePetRequestFilterDto() {

        PetRequestFilterDto dto = new PetRequestFilterDto("Dog", "Dog", PetStatus.AVAILABLE);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Dog", dto.name()),
                () -> Assertions.assertEquals("Dog", dto.category()),
                () -> Assertions.assertEquals(PetStatus.AVAILABLE, dto.status())
        );
    }
}