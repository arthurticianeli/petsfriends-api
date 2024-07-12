package com.arthurticianeli.petsfriends.applications.authentication.domain.entity;


import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "users_auth")
@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User(UserRequestDto userRequestDto) {
        this.name = userRequestDto.name();
        this.email = userRequestDto.email();
        this.password = userRequestDto.password();
        this.createdAt = LocalDateTime.now();
    }
}
