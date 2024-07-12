package com.arthurticianeli.petsfriends.applications.pets.controller;

import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestFilterDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetResponseDto;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
import com.arthurticianeli.petsfriends.applications.pets.service.PetServiceImpl;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1/pets")
@RequiredArgsConstructor
public class PetsController {

    @Autowired
    private PetRepository repository;

    @Autowired
    private PetServiceImpl service;

    @PostMapping
    public ResponseEntity<PetResponseDto> savePet(
            @RequestBody @Valid
            PetRequestDto petRequestDto,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        var pet = service.savePet(petRequestDto);

        var uri = uriComponentsBuilder.path("/v1/pets/{id}").buildAndExpand(pet.id()).toUri();

        return ResponseEntity.created(uri).body(pet);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PetResponseDto>> findAllPetsByFilterAndPagination(PetRequestFilterDto petRequestFilterDto, Pageable pageable) {
        return ResponseEntity.ok(service.findAllPetsByFilterAndPagination(petRequestFilterDto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> findPetById(@PathVariable Long id) {
        var possiblePet = repository.findById(id);
        if (possiblePet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.findPetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDto> updatePet(@PathVariable Long id, @RequestBody @Valid PetRequestDto petRequestDto) {
        return ResponseEntity.ok(service.updatePet(id, petRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        service.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PetResponseDto> updateStatus(@PathVariable Long id, @PathParam("status") String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
