package com.arthurticianeli.petsfriends.applications.pets.domain;

import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetResponseDto;
import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Period;

public class PetResponseDtoTest {

    @Test
    public void shouldCorrectlyMapFromPetEntity() {
        LocalDateTime birthDate = LocalDateTime.now().minusYears(2);
        PetRequestDto petRequestDto = new PetRequestDto(
                "Dog",
                "Dog",
                "Dog",
                new CategoryEntity("Dog"),
                birthDate,
                PetStatus.AVAILABLE
        );
        PetEntity petEntity = new PetEntity(petRequestDto);

        PetResponseDto dto = new PetResponseDto(petEntity);

        Assertions.assertEquals(petEntity.getId(), dto.id());
        Assertions.assertEquals(petEntity.getName(), dto.name());
        Assertions.assertEquals(petEntity.getDescription(), dto.description());
        Assertions.assertEquals(petEntity.getUrlImage(), dto.urlImage());
        Assertions.assertEquals(petEntity.getCategory(), dto.category());
        Assertions.assertEquals(petEntity.getBirthDate(), dto.birthDate());
        Assertions.assertEquals(Period.between(birthDate.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears(), dto.age().intValue());
        Assertions.assertEquals(petEntity.getStatus(), dto.status());
    }
}