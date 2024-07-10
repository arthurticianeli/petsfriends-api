package com.arthurticianeli.petsfriends.applications.category.domain.entities;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "categories")
@Entity(name = "category")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    private CategoryEntity category;

    public CategoryEntity(String category) {
        this.name = category;
    }

    public CategoryEntity(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.name();
    }
}
