package com.arthurticianeli.petsfriends.applications.pets.domain.entities;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Table(name = "pets")
@Entity(name = "pet")
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    private String description;
    @Column
    private String urlImage;
    @ManyToOne
    @NotNull
    private CategoryEntity category;
    @Column(name = "birth_date")
    @NotNull
    private LocalDateTime birthDate;
    @Column
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private PetStatus status;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    public PetEntity(PetRequestDto petRequestDto) {
        this.name = petRequestDto.name();
        this.description = petRequestDto.description();
        this.urlImage = petRequestDto.urlImage();
        this.category = petRequestDto.category();
        this.birthDate = petRequestDto.birthDate();
        this.status = petRequestDto.status();
        this.createdAt = LocalDateTime.now();
    }

    public Number calculateAge() {
        LocalDate birthDate = this.birthDate.toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    public void update(PetRequestDto petRequestDto) {
        this.name = petRequestDto.name();
        this.description = petRequestDto.description();
        this.urlImage = petRequestDto.urlImage();
        this.category = petRequestDto.category();
        this.birthDate = petRequestDto.birthDate();
        this.status = petRequestDto.status();
    }

    public void updateStatus(PetStatus status) {
        this.status = status;
    }
}
