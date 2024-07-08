package com.arthurticianeli.petsfriends.applications.pets.domain.service;

import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestFilterDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPetService {
    PetResponseDto savePet(PetRequestDto petRequestDto);
    PetResponseDto updatePet(Long id, PetRequestDto petRequestDto);
    void deletePet(Long id);
    PetResponseDto findPetById(Long id);
    Page<PetResponseDto> findAllPetsByFilterAndPagination(PetRequestFilterDto petRequestFilterDto, Pageable pageable);
    PetResponseDto updateStatus(Long id, String status);
}
