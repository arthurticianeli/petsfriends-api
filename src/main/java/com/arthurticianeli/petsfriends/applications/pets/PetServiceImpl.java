package com.arthurticianeli.petsfriends.applications.pets;

import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestFilterDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetResponseDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import com.arthurticianeli.petsfriends.applications.pets.domain.service.IPetService;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements IPetService {

    @Autowired
    private final PetRepository petRepository;

    @Transactional
    public PetResponseDto savePet(PetRequestDto petRequestDto) {
        var pet = new PetEntity(petRequestDto);
        return new PetResponseDto(petRepository.save(pet));
    }

    @Override
    @Transactional
    public PetResponseDto updatePet(Long id, PetRequestDto petRequestDto) {
        var petEntity = petRepository.getReferenceById(id);

        petEntity.update(petRequestDto);
        petRepository.save(petEntity);


        return new PetResponseDto(petEntity);
    }

    @Override
    @Transactional
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public Page<PetResponseDto> findAllPetsByFilterAndPagination(PetRequestFilterDto petRequestFilterDto, Pageable pageable) {

        Page<PetEntity> pets = petRepository.findAllPetsByFilterAndPagination(
                petRequestFilterDto.name(),
                petRequestFilterDto.category(),
                PetStatus.fromString(petRequestFilterDto.status()),
                pageable
        );

        List<PetResponseDto> petsResponse = pets.map(PetResponseDto::new).toList();

        return new PageImpl<>(
                petsResponse,
                pageable,
                pets.getTotalElements()
        );

    }

    public PetResponseDto findPetById(Long id) {
        return new PetResponseDto(petRepository.getReferenceById(id));
    }

    @Transactional
    public PetResponseDto updateStatus(Long id, String status) {

        var petEntity = petRepository.getReferenceById(id);
        var newStatus = PetStatus.fromString(status);

        if (newStatus == null) {
            throw new IllegalArgumentException("Invalid status");
        }

        petEntity.updateStatus(PetStatus.fromString(status));
        petRepository.save(petEntity);

        return new PetResponseDto(petEntity);
    }
}
