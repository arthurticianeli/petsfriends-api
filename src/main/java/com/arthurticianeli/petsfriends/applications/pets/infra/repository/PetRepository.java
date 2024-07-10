package com.arthurticianeli.petsfriends.applications.pets.infra.repository;

import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity, Long>, JpaSpecificationExecutor<PetEntity> {

    default Page<PetEntity> findAllPetsByFilterAndPagination(String name, String category, PetStatus status, Pageable pageable) {
        Specification<PetEntity> spec = Specification.where(PetSpecifications.nameContains(name))
                .and(PetSpecifications.categoryEquals(category))
                .and(PetSpecifications.statusEquals(status));

        return findAll(spec, pageable);
    }

    List<PetEntity> findByCategoryId(Long id);

}