package com.arthurticianeli.petsfriends.applications.pets.infra;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PetRepositoryTest {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TestEntityManager entityManager;

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

    @Test
    public void findByCategoryId_ShouldReturnPet_WhenPetExists() {
        entityManager.persist(categoryEntity);
        entityManager.persist(petEntity);
        entityManager.flush();

        PetEntity foundPet = petRepository.findByCategoryId(categoryEntity.getId()).get(0);

        assertThat(foundPet).isNotNull();
        assertThat(foundPet.getName()).isEqualTo(petEntity.getName());

    }

    @Test
    public void findAllPetsByFilterAndPagination_ShouldReturnPet_WhenPetExists() {

        String name = "Test Pet";
        PetStatus status = PetStatus.AVAILABLE;
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        entityManager.persist(categoryEntity);
        entityManager.persist(petEntity);
        entityManager.flush();

        Page<PetEntity> foundPet = petRepository
                .findAllPetsByFilterAndPagination(
                        name.toLowerCase(),
                        categoryEntity,
                        status,
                        pageable
                );

        assertThat(foundPet).isNotNull();
        assertThat(foundPet.getContent()).isNotEmpty();
        assertThat(foundPet.getContent().get(0).getName()).isEqualTo(petEntity.getName());
        assertThat(foundPet.getContent().get(0).getCategory().getName()).isEqualTo(petEntity.getCategory().getName());
    }
}
