package com.arthurticianeli.petsfriends.applications.authentication.infra.repository;

import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
