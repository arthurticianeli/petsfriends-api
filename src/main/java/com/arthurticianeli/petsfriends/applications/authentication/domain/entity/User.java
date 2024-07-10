package com.arthurticianeli.petsfriends.applications.authentication.domain.entity;


import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "users_auth")
@Entity(name = "user")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User(UserRequestDto userRequestDto) {
        this.name = userRequestDto.name();
        this.email = userRequestDto.email();
        this.password = userRequestDto.password();
    }
}
