package com.arthurticianeli.petsfriends.applications.pets.controller;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.controller.PetsController;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestFilterDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetResponseDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
import com.arthurticianeli.petsfriends.applications.pets.service.PetServiceImpl;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PetsControllerTest {

    @Mock
    private PetServiceImpl petService;

    @InjectMocks
    private PetsController petsController;

    @Mock
    private PetRepository petRepository;

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
    PetRequestFilterDto petRequestFilterDto = new PetRequestFilterDto(
            "Test Pet",
            "Test Category",
            PetStatus.AVAILABLE
    );

    @Test
    public void savePet_WhenPetDoesNotExist_ShouldReturnPet() throws Exception {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Mockito.when(petService.savePet(petRequestDto)).thenReturn(petResponseDto);

        var responseEntity = petsController.savePet(petRequestDto, uriComponentsBuilder);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Test Pet", responseEntity.getBody().name());
    }

    @Test
    public void savePet_WhenPetAlreadyExists_ShouldThrowDuplicatedTupleException() throws Exception {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Mockito.when(petService.savePet(petRequestDto)).thenThrow(new DuplicatedTupleException("Pet already exists"));

        Assertions.assertThrows(DuplicatedTupleException.class, () -> {
            petsController.savePet(petRequestDto, uriComponentsBuilder);
        });
    }

    @Test
    public void updatePet_WhenPetExists_ShouldReturnPet() throws Exception {
        Mockito.when(petService.updatePet(1L, petRequestDto)).thenReturn(petResponseDto);

        var responseEntity = petsController.updatePet(1L, petRequestDto);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Test Pet", responseEntity.getBody().name());
    }

    @Test
    public void deletePet() throws Exception {
        Mockito.doNothing().when(petService).deletePet(1L);

        var responseEntity = petsController.deletePet(1L);

        Assertions.assertNull(responseEntity.getBody());
    }

    @Test
    public void findAllPetsByFilterAndPagination() {
        Page<PetResponseDto> page = new PageImpl<>(Collections.singletonList(petResponseDto));
        var pageable = Mockito.mock(Pageable.class);
        Mockito.when(
                petService.findAllPetsByFilterAndPagination(
                        Mockito.any(),
                        Mockito.any()
                )
        ).thenReturn(page);

        var responseEntity = petsController.findAllPetsByFilterAndPagination(
                petRequestFilterDto,
                pageable
        );

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(1, responseEntity.getBody().getTotalElements());
        Assertions.assertEquals("Test Pet", responseEntity.getBody().getContent().get(0).name());
    }

    @Test
    public void findPetById_WhenPetExists_ShouldReturnPet() {

        Mockito.when(petService.findPetById(1L)).thenReturn(petResponseDto);
        Mockito.when(petRepository.findById(1L)).thenReturn(java.util.Optional.of(petEntity));

        ResponseEntity<PetResponseDto> responseEntity = petsController.findPetById(1L);

        Assertions.assertNotNull(responseEntity.getBody(), "The body of the response entity should not be null");
        Assertions.assertEquals("Test Pet", responseEntity.getBody().name(), "The name of the pet should match");
    }

    @Test
    public void updateStatus_WhenPetExists_ShouldReturnPet() {
        Mockito.when(petService.updateStatus(1L, "AVAILABLE"))
                .thenReturn(petResponseDto);

        var responseEntity = petsController.updateStatus(1L, "AVAILABLE");

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Test Pet", responseEntity.getBody().name());
    }
}
