package com.arthurticianeli.petsfriends.applications.pets.domain.entities;

import com.arthurticianeli.petsfriends.applications.pets.domain.dtos.PetRequestDto;
import com.arthurticianeli.petsfriends.applications.pets.domain.enums.PetStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Table(name = "pets")
@Entity(name = "pet")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String urlImage;
    @Column
    private String category;
    @Column
    private LocalDateTime birthDate;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private PetStatus status;
    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    public PetEntity(PetRequestDto petRequestDto) {
        this.name = petRequestDto.name();
        this.description = petRequestDto.description();
        this.urlImage = petRequestDto.urlImage();
        this.category = petRequestDto.category();
        this.birthDate = petRequestDto.birthDate();
        this.status = PetStatus.fromString(petRequestDto.status());
    }

    public Number calculateAge() {
        LocalDate birthDate = this.birthDate.toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    public void update(PetRequestDto petRequestDto) {
        if (petRequestDto.name() != null)
            this.name = petRequestDto.name();
        if (petRequestDto.description() != null)
            this.description = petRequestDto.description();
        if (petRequestDto.urlImage() != null)
            this.urlImage = petRequestDto.urlImage();
        if (petRequestDto.category() != null)
            this.category = petRequestDto.category();
        if (petRequestDto.birthDate() != null)
            this.birthDate = petRequestDto.birthDate();
        if (petRequestDto.status() != null)
            this.status = PetStatus.fromString(petRequestDto.status());
    }

    public void updateStatus(PetStatus status) {
        this.status = status;
    }
}
