package com.arthurticianeli.petsfriends.applications.pets.infra.repository;

import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecifications {

    public static Specification<PetEntity> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<PetEntity> categoryEquals(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%");
        };
    }

    public static Specification<PetEntity> statusEquals(PetStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}