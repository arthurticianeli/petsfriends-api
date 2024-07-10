package com.arthurticianeli.petsfriends.applications.authentication.domain.services;

import com.arthurticianeli.petsfriends.applications.authentication.domain.AccessToken;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    AccessToken save(User user);
    AccessToken authenticate(String email, String password);
}
