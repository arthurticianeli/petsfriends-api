package com.arthurticianeli.petsfriends.applications.pets.service;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.category.infra.repository.CategoryRepository;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestFilterDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetResponseDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private PetServiceImpl petService;

    CategoryEntity categoryEntity = new CategoryEntity("Test Category");
    PetRequestDto petRequestDto = new PetRequestDto(
            "Test Pet",
            "Test Breed",
            "url.com",
            categoryEntity,
            LocalDateTime.now(),
            PetStatus.AVAILABLE
    );
    PetEntity petEntity = new PetEntity(petRequestDto);
    PetResponseDto petResponseDto = new PetResponseDto(petEntity);
    PetRequestFilterDto petRequestFilterDto = new PetRequestFilterDto("Test Pet", "Test Category", PetStatus.AVAILABLE);

    @Test
    public void savePet_WhenPetDoesNotExist_ShouldReturnPet() {
        Mockito.when(petRepository.save(petEntity)).thenReturn(petEntity);

        PetResponseDto actualResponse = petService.savePet(petRequestDto);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(petResponseDto.name(), actualResponse.name());
    }

    @Test
    public void updatePet_WhenPetExists_ShouldReturnPet() {
        Mockito.when(petRepository.getReferenceById(1L)).thenReturn(petEntity);
        Mockito.when(petRepository.save(petEntity)).thenReturn(petEntity);

        PetResponseDto actualResponse = petService.updatePet(1L, petRequestDto);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(petResponseDto.name(), actualResponse.name());
    }

    @Test
    public void updatePet_WhenPetDoesNotExist_ShouldThrowEntityNotFoundException() {
        Mockito.when(petRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> petService.updatePet(1L, petRequestDto));
    }

    @Test
    public void deletePet_WhenPetExists_ShouldDeletePet() {
        Mockito.doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L);

        Mockito.verify(petRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void deletePet_WhenPetDoesNotExist_ShouldThrowEntityNotFoundException() {
        Mockito.doThrow(EntityNotFoundException.class).when(petRepository).deleteById(1L);

        Assertions.assertThrows(EntityNotFoundException.class, () -> petService.deletePet(1L));
    }

    @Test
    public void findAllPetsByFilterAndPagination_WhenPetsExists_ShouldReturnPets() {
        Mockito.when(petRepository.findAllPetsByFilterAndPagination(
                ArgumentMatchers.eq("Test Pet"),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(PetStatus.AVAILABLE),
                ArgumentMatchers.any(Pageable.class)
        )).thenReturn(new PageImpl<>(Collections.singletonList(petEntity)));


        Page<PetResponseDto> actualResponse = petService.findAllPetsByFilterAndPagination(
                petRequestFilterDto, Pageable.ofSize(10).withPage(0)
        );

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(1, actualResponse.getTotalElements());
    }

}
